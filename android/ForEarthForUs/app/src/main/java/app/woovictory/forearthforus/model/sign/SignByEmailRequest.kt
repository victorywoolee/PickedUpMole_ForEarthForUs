package app.woovictory.forearthforus.model.sign

/**
 * Created by VictoryWoo
 */
data class SignByEmailRequest(
    val email: String,
    val name: String,
    val password1: String,
    val password2: String
) {
    constructor() : this("", "", "", "")
}