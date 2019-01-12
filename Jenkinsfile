pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Notify') {
            steps {
                slackSend color: 'good', message: 'Building PoneCrafter branch ${branch} complete.'
            }
        }
    }
}