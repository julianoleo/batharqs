package com.batch.arqs.infra.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class BucketStorage {

    @Value("${common.sourcePath}")
    private String localPath;

    private final AmazonS3 s3Client;

    public BucketStorage(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public static final Logger LOGGER = LogManager.getLogger(BucketStorage.class);

    public void enviaArqBucket(String bucketName, String sourcePathBucket, String fileName) {
        File localFile = new File(localPath);
        try {
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    sourcePathBucket + fileName,
                    new File(localFile.getAbsolutePath() + "/" + fileName)
            );
            s3Client.putObject(request);
            LOGGER.info(" - Arquivo " + fileName + " enviado para o Bucket.");
        } catch (SdkClientException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public List<String> listaObjBucket(String bucketName) {
        List<String> result = new ArrayList<>();
        var objects = S3Objects.inBucket(s3Client, bucketName);
        objects.forEach((S3ObjectSummary s3ObjectSummary) -> {
            result.add(s3ObjectSummary.getKey());
        });
        return result;
    }

    public void baixaArquivosBucket(String bucketName, String sourcePath, String fileName) {
        File localFile = new File(localPath);
        try {
            InputStream arq = s3Client.getObject(new GetObjectRequest(bucketName, sourcePath + fileName)).getObjectContent();
            LOGGER.info(" - Diretório destino do Arquivo: " + Paths.get(localFile.getAbsolutePath() + "/" + fileName));
            Files.copy(arq, Paths.get(localFile.getAbsolutePath() + "/" + fileName));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void deletaObjeto(String bucketName, String sourcePathBucket, String fileName) {
        try {
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName)
                    .withKeys(sourcePathBucket + fileName)
                    .withQuiet(false);
            s3Client.deleteObjects(deleteObjectsRequest);
            LOGGER.info(" - Arquivo " + fileName + " deletado do bucket.");
        } catch (SdkClientException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
