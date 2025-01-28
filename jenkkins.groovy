pipeline{
    agent any
    //parmerterization
    parameters{
        string(name : 'BRANCH' , defaultValue : 'main', description : 'Branch to build')
        choice(name : 'ENVIRONMENT', choices: ['DEV', 'PROD', 'QA'], description : 'Deployment environment')
        booleanParam(name : 'RUN_TESTS', defaultValue: true, description: 'should tests run?')
    }

    stages{
        stage('Print Parameters'){
            steps{
                echo "Selected Branch : ${params.BRANCH}"
                echo "Selected Env : ${params.ENVIRONMENT}"
                echo "Run Tests : ${params.RUN_TESTS}"
            }
        }
        stage('Environment setup'){
            steps{
                echo "agent is ${getAgentForEnvironment()}"
                script{
                    switch(params.ENVIRONMENT){
                        case 'DEV' :
                            echo "Setting up dev environment"
                            echo "Dev environment setup completed"

                            break

                        case 'PROD':
                            echo "Setting up PROD environment"
                            echo "PROD envionment setup completed"

                            break

                        case 'QA':
                            echo "Setting up QA environment"
                            echo "QA Envionment setup completed"

                            break

                        default:
                            echo "Unkown environmewnt selected"
                    }
                }
            }
        }

        stage('Run Tests'){
            when{
                expression { params.RUN_TESTS}
            }

            steps{
                echo "Running tests as required"
                echo "Tests executed on Env :  ${params.ENVIRONMENT}"
            }
        }

        stage("Timeout and retry mechanism"){
            options{
                timeout(time:10, unit : 'SECONDS')
            }
            steps{
                script{
                    retry(100){
                        echo "Hello world ...."
                        sh njsdancvld ndacladsc
                    }
                }
            }
        }

        stage('Deploy'){
            steps{
                echo "Deploying on ${params.ENVIRONMENT} environment......"
            }
        }

    }

    post{
        always{
            echo "Pipeline execution finished"
        }
        success{
            echo "Pipeline successed on branch ${params.BRANCH} and on environment ${params.ENVIRONMENT}"
        }
        failure{
            echo "Pipeline failed. Check Logs"
        }
    }
}

def getAgentForEnvironment(){
    if (params.ENVIRONMENT == 'DEV') return "DevAgent"
    else if(params.ENVIRONMENT == 'QA') return "QAAgent"
    else if(params.ENVIRONMENT == 'PROD') return "ProdAgent"
    else return "defaultAgent"
}