export type AmplifyDependentResourcesAttributes = {
    "api": {
        "taskmaster": {
            "GraphQLAPIKeyOutput": "string",
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    },
    "auth": {
        "taskmaster64d0979c": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string"
        }
    },
    "storage": {
        "s3fa89402a": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "analytics": {
        "taskmaster": {
            "Region": "string",
            "Id": "string",
            "appName": "string"
        }
    },
    "predictions": {
        "translateTextaf3c5022": {
            "region": "string",
            "sourceLang": "string",
            "targetLang": "string"
        },
        "speechGenerator4d1d1825": {
            "region": "string",
            "language": "string",
            "voice": "string"
        }
    }
}