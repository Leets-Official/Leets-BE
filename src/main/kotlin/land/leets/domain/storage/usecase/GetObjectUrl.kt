package land.leets.domain.storage.usecase

interface GetObjectUrl {
    fun execute(fileName: String): String
}
