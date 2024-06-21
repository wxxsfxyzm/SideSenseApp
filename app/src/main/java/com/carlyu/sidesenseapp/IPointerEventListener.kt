package com.carlyu.sidesenseapp

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

interface IPointerEventListener : IInterface {
    fun onDownEvent(f: Float, f2: Float)
    fun onUpEvent(f: Float, f2: Float)

    companion object {
        const val DESCRIPTOR: String = "com.carlyu.sidesenseapp.IPointerEventListener"

        fun asInterface(iBinder: IBinder?): IPointerEventListener? {
            if (iBinder == null) {
                return null
            }
            val queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR)
            return if (queryLocalInterface == null || queryLocalInterface !is IPointerEventListener) {
                Stub.Proxy(iBinder)
            } else {
                queryLocalInterface
            }
        }
    }

    class Default : IPointerEventListener {
        override fun asBinder(): IBinder? {
            return null
        }

        override fun onDownEvent(f: Float, f2: Float) {
        }

        override fun onUpEvent(f: Float, f2: Float) {
        }
    }

    abstract class Stub : Binder(), IPointerEventListener {
        companion object {
            const val TRANSACTION_onDownEvent = 1
            const val TRANSACTION_onUpEvent = 2
        }

        init {
            attachInterface(this, DESCRIPTOR)
        }

        override fun asBinder(): IBinder {
            return this
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if (code in 1..16777215) {
                data.enforceInterface(DESCRIPTOR)
            }
            return when (code) {
                1598968902 -> {
                    reply?.writeString(DESCRIPTOR)
                    true
                }

                TRANSACTION_onDownEvent -> {
                    onDownEvent(data.readFloat(), data.readFloat())
                    reply?.writeNoException()
                    true
                }

                TRANSACTION_onUpEvent -> {
                    onUpEvent(data.readFloat(), data.readFloat())
                    reply?.writeNoException()
                    true
                }

                else -> super.onTransact(code, data, reply, flags)
            }
        }

        class Proxy(private val mRemote: IBinder) : IPointerEventListener {
            override fun asBinder(): IBinder {
                return mRemote
            }

            override fun onDownEvent(f: Float, f2: Float) {
                val obtain = Parcel.obtain()
                val obtain2 = Parcel.obtain()
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR)
                    obtain.writeFloat(f)
                    obtain.writeFloat(f2)
                    mRemote.transact(TRANSACTION_onDownEvent, obtain, obtain2, 0)
                    obtain2.readException()
                } finally {
                    obtain2.recycle()
                    obtain.recycle()
                }
            }

            override fun onUpEvent(f: Float, f2: Float) {
                val obtain = Parcel.obtain()
                val obtain2 = Parcel.obtain()
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR)
                    obtain.writeFloat(f)
                    obtain.writeFloat(f2)
                    mRemote.transact(TRANSACTION_onUpEvent, obtain, obtain2, 0)
                    obtain2.readException()
                } finally {
                    obtain2.recycle()
                    obtain.recycle()
                }
            }
        }
    }
}
