pipeline {
    agent any
    stages {
        stage ('Init') {
            steps {
                slackSend color: '#0000FF', message: "Started build ${env.JOB_NAME} ${env.BUILD_NUMBER}. (<${env.BUILD_URL}|Open>)"
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts(artifacts: 'target/*.jar', onlyIfSuccessful: true, fingerprint: true)
            }
        }
    }
    post {
        success {
            slackSend color: 'good', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} completed successfully. (<${env.BUILD_URL}|Open>)"
        }
        unstable {
            slackSend color: 'warning', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} completed, but is unstable. (<${env.BUILD_URL}|Open>)"
        }
        unsuccessful {
            slackSend color: 'danger', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} failed. (<${env.BUILD_URL}|Open>)"
        }
        aborted {
            slackSend color: '#888888', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} aborted. (<${env.BUILD_URL}|Open>)"
        }
    }
}