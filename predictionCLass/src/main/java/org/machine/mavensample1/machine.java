package org.machine.mavensample1;

import java.io.IOException;
import java.util.List;

public class machine{
   public static List<String> Predict(String pathi,String typePrediction,String targets) throws IOException {
	    String typePred=typePrediction;
	    String path=pathi;
	    String target=targets;
	    List<String> result = null;
	    switch (typePred) {
            case "multi_label":
            	multiClassification pred=new multiClassification();
            	result=pred.bestModel(path,target);
            	break;

            case "regression":
            	regression predReg=new regression();
            	result=predReg.bestModel(path,target);
            	break;

            case "binary_classification":
            	classificatio predBin=new classificatio();
            	result=predBin.bestModel(path,target);
            	break;

            default:
                System.out.println("Invalid task type!");
                break;
        }
	    return result;
    }
    	
 }    

   
  
