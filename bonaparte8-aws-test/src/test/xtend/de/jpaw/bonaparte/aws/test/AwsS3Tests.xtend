package de.jpaw.bonaparte.aws.test

import org.testng.annotations.Test

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import static extension org.testng.Assert.*

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
    
    def public void testS3upload() {
        val id = Long.toString(System.currentTimeMillis, 16)
        val s3client = createClient
        s3client.putObject(new PutObjectRequest(MY_BUCKET, id, "sgfksdhfksdhf"))
    }
}