pipeline {
    agent any
    stages {
        stage ('Init') {
            steps {
                slackSend color: '#0000FF', message: "Started build ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) for branch ${env.BRANCH_NAME}."
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
            slackSend color: 'good', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) for branch ${env.BRANCH_NAME} completed successfully."
        }
        unstable {
            slackSend color: 'warning', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) for branch ${env.BRANCH_NAME} completed, but is unstable."
        }
        unsuccessful {
            slackSend color: 'danger', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) for branch ${env.BRANCH_NAME} failed."
        }
        aborted {
            slackSend color: '#888888', message: "Build ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>) for branch ${env.BRANCH_NAME} aborted."
        }
    }
}