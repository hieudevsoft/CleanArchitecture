package hieubui.eup.base_clean_architecture.domain.failure

abstract class Failure(val errorMessage:String?=null)

class ServerFailure(errorMessage: String?) : Failure(errorMessage)
class InternetFailure(errorMessage: String?) : Failure(errorMessage)
class GeneralFailure(errorMessage: String?) : Failure(errorMessage)
class ParserFailure(errorMessage: String?) : Failure(errorMessage)
