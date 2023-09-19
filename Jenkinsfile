def branchEnv = ''
def pathEnv = ''
def pathDev = ''
def pathQA = ''
def pathMaster = ''
def repoName = ''
def gitUrl = ''

pipeline {
    agent any

    tools {
        gradle '8.3-rc-4' // You need to add a maven with name "8.3-rc-4" in the Global Tools Configuration page
    }

    environment {
        BRANCH_NAME = "${GIT_BRANCH}"
      	GIT_URL = "${GIT_URL}"
    }

    stages {

        stage('Gradle Build') {
            when {
                anyOf {
                    branch 'main'
                }
            }
            steps {
                script {
                    branchEnv = BRANCH_NAME
                    gitUrl = GIT_URL
                  	repoName = gitUrl.tokenize('/').last().split("\\.")[0]
                }
                echo "Running Build ${branchEnv}"
              	echo "Running Repo ${gitUrl}"
              	echo "Running Repo ${repoName}"
                sh 'gradle -v'
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }

        stage("Build docker image") {
            when {
                branch 'main'
            }
            steps {
                echo "Running Build Docker image ${branchEnv}"
                sh "docker build -t ${repoName} ."
                sh "docker tag ${repoName} localhost:5000/${repoName}"
                sh "docker push localhost:5000/${repoName}"
                sh "docker image remove ${repoName}"
                sh "docker image remove localhost:5000/${repoName}"
            }
        }

        stage("Deploy Docker Stack") {
            when {
                branch 'main'
            }
            steps {
                echo "Running Deploy Docker Stack ${branchEnv}"
                sh "docker stack deploy -c stack.yaml ${repoName} --resolve-image always"
            }
        }

    }

    post {
        success {
            slackSend color: '#36a64f', message: "Deployment of ${repoName} to ${branchEnv} succeeded!"
        }
        failure {
            slackSend color: '#ff0000', message: "Deployment of ${repoName} to ${branchEnv} failed!"
        }
        always {
            cleanWs()
        }
    }
}