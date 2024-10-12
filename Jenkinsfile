node("ci-node"){

    def GIT_COMMIT_HASH = ""

    stage("checkout"){
        checkout scm
        GIT_COMMIT_HASH = sh (script: "git log -n 1 --pretty=format:'%H'", returnStdout: true)
    }

//    stage("tests"){
//        sh "chmod 777 mvnw && ./mvnw test"
//    }


    stage("build"){
        sh "chmod 777 mvnw && ./mvnw package -DskipTests"
    }

    stage("Build docker Image"){
        sh "sudo docker build -t mchekini/scpi-invest-api:$GIT_COMMIT_HASH ."
    }

    stage("Push Docker Image"){
        withCredentials([usernamePassword(credentialsId: 'mchekini-docker-hub', passwordVariable: 'password', usernameVariable: 'username')]) {
            sh "sudo docker login -u $username -p $password"
            sh "sudo docker push mchekini/scpi-invest-api:$GIT_COMMIT_HASH"
            sh "sudo docker rmi mchekini/scpi-invest-api:$GIT_COMMIT_HASH"
        }
    }
}