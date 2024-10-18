node("ci-node"){

    def GIT_COMMIT_HASH = ""

    stage("Checkout"){
        checkout scm
        GIT_COMMIT_HASH = sh (script: "git log -n 1 --pretty=format:'%H'", returnStdout: true)
    }

    stage("Tests unitaires"){
        sh "chmod 777 mvnw && ./mvnw test"
    }

    stage("Quality Analyses"){

        withCredentials([usernamePassword(credentialsId: 'sonar-token', passwordVariable: 'password', usernameVariable: 'username')]) {
                 sh "./mvnw clean verify sonar:sonar \\\n" +
                         "  -Dsonar.projectKey=scpi-invest-api \\\n" +
                         "  -Dsonar.projectName='scpi-invest-api' \\\n" +
                         "  -Dsonar.host.url=https://sonarqube.check-consulting.net \\\n" +
                         "  -Dsonar.token=$password"
        }
    }


    stage("Build Jar File"){
        sh "./mvnw package -DskipTests"
    }

    stage("Archive Jar files"){
        archiveArtifacts artifacts: 'target/scpi-invest-*.jar', followSymlinks: false
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