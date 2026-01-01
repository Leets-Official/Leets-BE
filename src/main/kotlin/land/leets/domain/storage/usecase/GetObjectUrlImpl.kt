package land.leets.domain.storage.usecase

import com.oracle.bmc.objectstorage.ObjectStorage
import org.springframework.beans.factory.annotation.Value


class GetObjectUrlImpl(
    private val objectStorage: ObjectStorage,
    @Value("\${oci.bucket.name}") private val bucketName: String,
    @Value("\${oci.bucket.namespace}") private val bucketNamespace: String,
) : GetObjectUrl {

    override fun execute(fileName: String): String =
        "${objectStorage.endpoint}/n/$bucketNamespace/b/$bucketName/o/$fileName"
}
