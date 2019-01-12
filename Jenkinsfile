pipeline {
    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'Hello Fluttershy!'
            }
        }
        stage('Notify') {
            steps {
                slackSend color: 'good', message: 'Testing "Slack" notifications...'
            }
        }
    }
}