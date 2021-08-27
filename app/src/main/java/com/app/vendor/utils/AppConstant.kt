package com.app.vendor.utils

object AppConstant {

    const val DETECT_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val DEVICE_TYPE = 1
    const val SPLASH_DELAY: Long = 1000
    const val PERMISSION_CODE=4

    interface PKN {
        companion object {
            const val ACCESS_TOKEN = "access_token"
        }
    }


    interface MT {
        companion object {
            const val UPLOAD_MEDIA = "images"
            const val TEXT_PLAIN = "text/plain"
        }
    }

    interface TYPE {
        companion object {
            const val CAMERA_IMAGE = 0
            const val GALLERY_IMAGE = 1
            const val CAMERA_VIDEO = 2
            const val GALLERY_VIDEO = 3
            const val DOCS = 4
            const val AUDIO = 5
        }
    }

}