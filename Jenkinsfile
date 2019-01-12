pipeline {
    agent any
    stages {
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
        stage('Notify') {
            steps {
                slackSend color: 'good', message: 'Building PoneCrafter branch ${branch} complete.'
            }
        }
    }
}