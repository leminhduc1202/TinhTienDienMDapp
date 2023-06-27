package mdideas.devapp.tinhtiendienmdapp.model

data class EvnResponse(
    val id : Int? = 0,
    val successful : Boolean? = false,
    val listEvn : ArrayList<EvnData>? = arrayListOf(),
    val evnTyped : String ? = ""
)
