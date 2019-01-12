pipeline {
    agent any
    stages {
        stage ('Init') {
            steps {
                slackSend color: '#0000FF', message: "Started build ${env.BUILD_NUMBER} for branch ${env.BRANCH_NAME}."
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts(artifacts: 'target/PoneCrafter-1.0-SNAPSHOT-jar-with-dependencies.jar', onlyIfSuccessful: true, fingerprint: true)
            }
        }
    }
    post {
        success {
            slackSend color: 'good', message: "Build ${env.BUILD_NUMBER} for branch ${env.BRANCH_NAME} completed successfully."
        }
        unstable {
            slackSend color: 'warning', message: "Build ${env.BUILD_NUMBER} for branch ${env.BRANCH_NAME} completed, but is unstable."
        }
        unsuccessful {
            slackSend color: 'danger', message: "Build ${env.BUILD_NUMBER} for branch ${env.BRANCH_NAME} failed."
        }
        aborted {
            slackSend color: '#888888', message: "Build ${env.BUILD_NUMBER} for branch ${env.BRANCH_NAME} aborted."
        }
    }
}