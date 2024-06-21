package com.carlyu.sidesenseapp.tutorial

import android.content.Context
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView

/* loaded from: classes.dex */
class TutorialVideoView : TextureView {
    private var mAspect: Float
    private val mMatrix: Matrix
    private var mMediaContentsId = 0
    private var mMediaPlayer: MediaPlayer? = null
    private var mSurfaceTexture: SurfaceTexture? = null
    private var mTextureToDestroy: SurfaceTexture? = null

    constructor(context: Context?) : super(context!!) {
        this.mMatrix = Matrix()
        this.mAspect = 1.0f
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(
        context!!, attributeSet
    ) {
        this.mMatrix = Matrix()
        this.mAspect = 1.0f
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet, i
    ) {
        this.mMatrix = Matrix()
        this.mAspect = 1.0f
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int, i2: Int) : super(
        context!!, attributeSet, i, i2
    ) {
        this.mMatrix = Matrix()
        this.mAspect = 1.0f
    }

    fun createSurfaceTextureListener(i: Int): SurfaceTextureListener {
        return object : SurfaceTextureListener {
            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialVideoView.1
            // android.view.TextureView.SurfaceTextureListener
            override fun onSurfaceTextureAvailable(
                surfaceTexture: SurfaceTexture,
                i2: Int,
                i3: Int
            ) {
                this@TutorialVideoView.mSurfaceTexture = surfaceTexture
                this@TutorialVideoView.createVideo(i)
                this@TutorialVideoView.requestLayout()
                this@TutorialVideoView.startVideo()
            }

            // android.view.TextureView.SurfaceTextureListener
            override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
                this@TutorialVideoView.mTextureToDestroy = surfaceTexture
                this@TutorialVideoView.stopVideo()
                mSurfaceTexture!!.release()
                return false
            }

            // android.view.TextureView.SurfaceTextureListener
            override fun onSurfaceTextureSizeChanged(
                surfaceTexture: SurfaceTexture,
                i2: Int,
                i3: Int
            ) {
            }

            // android.view.TextureView.SurfaceTextureListener
            override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {
            }
        }
    }

    fun createVideo(i: Int) {
        val videoUri = getVideoUri(context, i)
        val mediaPlayer = this.mMediaPlayer
        mediaPlayer?.release()
        val surfaceTexture = this.mTextureToDestroy
        if (surfaceTexture != null) {
            surfaceTexture.release()
            this.mTextureToDestroy = null
        }
        val create = MediaPlayer.create(context, videoUri)
        this.mMediaPlayer = create
        if (create == null) {
            return
        }
        create.setSurface(Surface(this.mSurfaceTexture))
        mMediaPlayer!!.setOnPreparedListener { mediaPlayer2 ->
            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialVideoView.2
            // android.media.MediaPlayer.OnPreparedListener
            mediaPlayer2.isLooping = true
        }
        mMediaPlayer!!.setOnInfoListener { mediaPlayer2, i2, i3 ->

            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialVideoView.3
            // android.media.MediaPlayer.OnInfoListener
            if (i2 == 3) {
                this@TutorialVideoView.visibility = 0
            }
            false
        }
        mMediaPlayer!!.setOnErrorListener { mediaPlayer2, i2, i3 ->

            // from class: com.sonymobile.sidesenseapp.tutorial.TutorialVideoView.4
            // android.media.MediaPlayer.OnErrorListener
            val tutorialVideoView = this@TutorialVideoView
            tutorialVideoView.surfaceTextureListener =
                tutorialVideoView.createSurfaceTextureListener(i)
            false
        }
    }

    // android.view.View
    public override fun onFinishInflate() {
        super.onFinishInflate()
    }

    // android.view.View
    public override fun onMeasure(i: Int, i2: Int) {
        var _i2 = i2
        if (MeasureSpec.getMode(_i2) != MeasureSpec.EXACTLY) {
            _i2 = MeasureSpec.makeMeasureSpec(
                Math.round(MeasureSpec.getSize(i) * this.mAspect),
                MeasureSpec.EXACTLY
            )
        } else {
            val size = MeasureSpec.getSize(i)
            val size2 = MeasureSpec.getSize(_i2)
            if (size == 0) {
                return
            }
            val f = size.toFloat()
            mMatrix.setScale(size2 / (this.mAspect * f), 1.0f, f / 2.0f, 0.0f)
            setTransform(this.mMatrix)
        }
        super.onMeasure(i, _i2)
    }

    fun pauseVideo() {
        val mediaPlayer = this.mMediaPlayer
        if (mediaPlayer == null || !mediaPlayer.isPlaying) {
            return
        }
        mMediaPlayer!!.pause()
    }

    fun resetVideo() {
        val mediaPlayer = this.mMediaPlayer
        if (mediaPlayer == null || !mediaPlayer.isPlaying) {
            return
        }
        mMediaPlayer!!.seekTo(0)
        startVideo()
    }

    fun setTutorialCategory(i: Int) {
        val f = if (i == 0) {
            PAGE_CATEGORY_TUTORIAL_PAGE_ASPECT
        } else if (i != 1) {
            return
        } else {
            PAGE_CATEGORY_PRACTICE_PAGE_ASPECT
        }
        this.mAspect = f
    }

    fun startVideo() {
        val mediaPlayer = this.mMediaPlayer
        if (mediaPlayer == null || mediaPlayer.isPlaying) {
            return
        }
        mMediaPlayer!!.start()
    }

    fun stopVideo() {
        val mediaPlayer = this.mMediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.stop()
            mMediaPlayer!!.release()
            this.mMediaPlayer = null
        }
    }

    fun updateVideo(i: Int) {
        this.mMediaContentsId = i
        if (this.mSurfaceTexture == null) {
            surfaceTextureListener = createSurfaceTextureListener(i)
        } else {
            startVideo()
        }
    }

    companion object {
        const val PAGE_CATEGORY_PRACTICE_PAGE: Int = 1
        private const val PAGE_CATEGORY_PRACTICE_PAGE_ASPECT = 0.75f
        const val PAGE_CATEGORY_TUTORIAL_PAGE: Int = 0
        private const val PAGE_CATEGORY_TUTORIAL_PAGE_ASPECT = 1.3333334f
        private const val TAG = "TutorialVideoView"
        fun getVideoUri(context: Context, i: Int): Uri {
            val stringBuilder: StringBuilder = StringBuilder("android.resource://")
            stringBuilder.append(context.packageName)
            stringBuilder.append("/")
            stringBuilder.append(i)
            return Uri.parse(stringBuilder.toString())
        }
    }
}