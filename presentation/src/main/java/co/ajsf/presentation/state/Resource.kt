package co.ajsf.presentation.state

data class Resource<out T>(val status: ResourceState, val data: T?, val message: String?)