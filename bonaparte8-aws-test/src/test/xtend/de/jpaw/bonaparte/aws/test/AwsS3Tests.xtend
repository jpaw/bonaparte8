package de.jpaw.bonaparte.aws.test

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import de.jpaw.bonaparte.core.CompactByteArrayComposer
import de.jpaw.bonaparte.pojos.s3Tests.Test1
import java.io.ByteArrayInputStream
import org.testng.annotations.Test

import static extension org.testng.Assert.*
import com.amazonaws.services.s3.model.ListObjectsRequest

@Test
class AwsS3Test {
    private static val MY_BUCKET = "my1st-bucket"
    
    def private createClient() {
        val credentials = (new ProfileCredentialsProvider).credentials
        return new AmazonS3Client(credentials)
    }
    
    def public void testCreateBucket() {
        val s3client = createClient
        val bucket = s3client.createBucket(MY_BUCKET)
        bucket.assertNotNull
    }
    
    
    def public void testListBuckets() {
        createClient.listBuckets.forEach [
            println('''got bucket «name»''')
        ]
    }
    
    def public void testListFolder() {
        val s3client = createClient
        val listObjectsRequest = new ListObjectsRequest().withBucketName(MY_BUCKET).withPrefix("AwsS3Test/").withDelimiter("#");
        val objects = s3client.listObjects(listObjectsRequest)
        objects.objectSummaries.forEach[
            println('''S3 object «key» (by «owner») has size «size» and class «storageClass»''')
        ]
    }
    
    def public void testS3upload() {
        val id = Long.toString(System.currentTimeMillis, 16)
        val s3client = createClient
        
        val data = new Test1(System.currentTimeMillis, 12, "hello, world")
        val binaryData = CompactByteArrayComposer.marshal(Test1.meta$$this, data)
        val stream = new ByteArrayInputStream(binaryData)
        val meta = new ObjectMetadata => [
            contentLength = binaryData.length
            contentType   = "application/bonaparte"
        ]
        s3client.putObject(MY_BUCKET, "AwsS3Test/S3upload-" + id, stream, meta);
    }
}