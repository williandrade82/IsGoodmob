package br.com.fiap.isgood.model

import br.com.fiap.isgood.R
import com.google.android.gms.maps.model.LatLng

data class Restaurante(
    var id: String,
    val nome: String,
    val apresentacao:String,
    val endereco: String,
    val rating : Float,
    val latitude: Double,
    val longitude: Double,
    val strLogoRestaurante : String,
    val socialLinksArrayList: ArrayList<SocialLinks>,
) {
    data class SocialLinks(
        val kind : Int,
        val toastText : String,
        val publicUri : String,
    ) {
        companion object{
            val KIND_FACEBOOK = R.drawable.img_vector_logo_facebook
            val KIND_INSTAGRAM = R.drawable.img_vector_logo_instagram_gray
            val KIND_YOUTUBE = R.drawable.img_vector_logo_youtube
            val KIND_WHATSAPP = R.drawable.img_vector_logo_whatsapp
            val KIND_LINKTREE = R.drawable.img_vector_logo_linktree
            val KIND_SITE = R.drawable.img_vector_logo_site
            val KIND_IFOOD = R.drawable.img_vector_logo_ifood
            val KIND_TRIPADVISOR = R.drawable.img_vector_logo_site

            fun getAppString(kind : Int) : String{
                when (kind) {
                    KIND_WHATSAPP ->
                        return "com.whatsapp"
                    KIND_FACEBOOK ->
                        return "com.facebook"
                    else ->
                        return "com.google.chrome"
                }
            }
        }
    }
    fun getLatLng(): LatLng {
        if (latitude.equals(null) or longitude.equals(null))
            throw NullPointerException("Latitude ou longitude não declaradas.")
        return LatLng(latitude, longitude)

    }

}