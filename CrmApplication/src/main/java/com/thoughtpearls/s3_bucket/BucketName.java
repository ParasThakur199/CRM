package com.thoughtpearls.s3_bucket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("thoughtpearlscrmbucket");
    private final String bucketName;
}
