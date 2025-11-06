package com.passman.helper

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.security.crypto.EncryptedFile
import com.passman.BuildConfig
import java.io.File
import java.security.MessageDigest

fun logit(msg: Any? = "...") {
    if (BuildConfig.DEBUG) {
        val trace: StackTraceElement? = Thread.currentThread().stackTrace[3]
        val lineNumber = trace?.lineNumber
        val methodName = trace?.methodName
        val className = trace?.fileName?.replaceAfter(".", "")?.replace(".", "")

        Log.d("Line $lineNumber", "$className::$methodName() L$lineNumber -> $msg")
    }
}

fun Boolean?.isTrue() = this == true
fun Boolean?.isFalse() = this == false

fun Context.getPassphrase(): ByteArray {
    val file = File(filesDir, "passphrase.bin")

//    val mKey = generateMasterKey()

    val encryptedFile = EncryptedFile.Builder(
        file,
        this,
//            mKey,
        String(computeSha256DigestBytes(getPackageInfo().getSignatures().last().toByteArray())),
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()

    return if (file.exists()) {
        encryptedFile.openFileInput().use { it.readBytes() }
    } else {
        generatePassphrase().also { passphrase ->
            encryptedFile.openFileOutput().use { it.write(passphrase) }
        }
    }
}
@Suppress("DEPRECATION")
private fun Context.getPackageInfo(): PackageInfo {
    return packageManager.getPackageInfo(
        packageName,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) PackageManager.GET_SIGNING_CERTIFICATES
        else PackageManager.GET_SIGNATURES)

}
private fun computeSha256DigestBytes(data: ByteArray): ByteArray {
    val messageDigest: MessageDigest = MessageDigest.getInstance("SHA256")
    messageDigest.update(data)
    return messageDigest.digest()
}
private fun PackageInfo.getSignatures(): Array<out Signature> {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) signingInfo?.apkContentsSigners as Array<out Signature>
    else signatures as Array<out Signature>
}

private fun generatePassphrase(): ByteArray {
    val random = SecureRandomStrengthener.getInstance("SHA1PRNG").generateAndSeedRandomNumberGenerator()
    val result = ByteArray(32)

    random.nextBytes(result)
    while (result.contains(0)) {
        random.nextBytes(result)
    }

    return result
}

fun String?.isEqualExt(str: String?): Boolean {
    return this?.equals(str,true) == true
}

infix fun Context.showToast(msg: Any?) {
    val m = msg.toString()
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(this, m, if (m.length < 15) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    } else {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, m, if (m.length < 15) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
                .show()
        }
    }
}