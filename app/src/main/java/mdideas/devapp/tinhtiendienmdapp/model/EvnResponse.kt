package mdideas.devapp.tinhtiendienmdapp.model

data class EvnResponse(
    val successful : Boolean? = false,
    val listEvn : ArrayList<EvnData>? = arrayListOf(),
)

data class EvnTaxResponse(
    val evnTaxUpdate : Int ?= 0
)
