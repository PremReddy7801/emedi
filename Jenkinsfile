pipeline{
	agent any
		
		stages{
			stage('build'){
			   steps{
				sh "mvn clean"
				}
			     }
			stage('COdeAnalysis'){
					 steps{ junit allowEmptyResults:true, '**/target/*.xml' }  
					  }
			}
}