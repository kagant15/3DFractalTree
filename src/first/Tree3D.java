package first;

import java.applet.Applet;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Cylinder;

@SuppressWarnings("serial")
public class Tree3D extends Applet{

	BranchGroup branches;
	
	public static void main(String[] args) {
		new Tree3D(); 
	}

	public Tree3D() {
		
		branches = new BranchGroup();
		// Create the universe
		SimpleUniverse universe = new SimpleUniverse();
	
//		BranchGroup scene = createSceneGraph();
		
		makeTree(2, 3, 0, 0.6, 0.05f);
		BranchGroup scene = branches;
		
		
		// adds the light to the group
		scene.addChild(createLight());
		// look towards the tree
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
	
	}
	
	public void makeTree(int B, int level, double x1, double y1, float radius2){
		if(level==0){
    		return;
    	}
    		
    	double theta=Math.PI/B;
    	float length=0.5f;
    	float radius=radius2;
		
    	for(int i=0; i<=B-1; i++){
    		double beta=theta*i;

    		double x2=x1+length*Math.cos(Math.random()*theta+beta);
    		double y2=y1-length*Math.sin(Math.random()*theta+beta);
    		double X2= (x2+x1)/2;
       	 	double Y2= (y2+y1)/2;
       	 
       	 	double angle= Math.PI/2-(Math.atan((y2-y1)/(x2-x1)));
       	 	
       	 	Transform3D rotateZClkwise = new Transform3D();
       	 				rotateZClkwise.rotZ(angle); 		//rotate clockwise
       	 	
       	 	//calculates the length of the cylinder using the midpoint formula
       	 	float height=(float) Math.sqrt((Math.pow((x1-x2), 2))+(Math.pow((y1-y2),2)));
       	 	
       	 	//makes the cylinder
    		Cylinder branch= new Cylinder(radius, height);
    		
    		//creates the next location for the cylinder
    		Vector3f vector = new Vector3f((float)X2, (float)(-1*Y2), 0);
    		Transform3D move = new Transform3D();
    					move.setTranslation(vector);
    					move.mul(rotateZClkwise);
    					
    		//Combines the move and the branch	  
    		TransformGroup firstBranchGrp = new TransformGroup();
    		    		   firstBranchGrp.setTransform(move);
    		    		   firstBranchGrp.addChild(branch);
    		    		   
    		
    		//Draws the 2D lines			
    		LineArray myLine= new LineArray (2, LineArray.COORDINATES);
    				  myLine.setCoordinate(0, new Point3f((float) (x1), (float)(-1*y1), 0.0f));
    				  myLine.setCoordinate(1, new Point3f((float) x2, (float) (-1*y2), 0.0f));		  
    		  		
    		// add the cylinder
    		branches.addChild(firstBranchGrp);
    		// add the line
//    		branches.addChild(new Shape3D(myLine));
    		
    		
    		makeTree(B, level-1, x2, y2, radius-.01f);
    	}
    	
		
	}
	
	public BranchGroup createSceneGraph(){
		//The cylinders we've created with float radius and float height
		Cylinder cylinderBase = new Cylinder(0.4f, 1.5f);
		Cylinder cylinderBranch = new Cylinder(0.2f, 1.2f);
		Cylinder cylinderBranch2 = new Cylinder(0.2f, 1.2f);

		Transform3D rotateX = new Transform3D();			//Rotation for branch
					rotateX.rotX(Math.PI/8);				//This rotation leans the entire tree forward so it can be seen at an angle	
		Transform3D rotateZCounterClk = new Transform3D();
					rotateZCounterClk.rotZ(Math.PI/-3);		//rotate counter clockwise
		Transform3D rotateZClkwise = new Transform3D();
					rotateZClkwise.rotZ(Math.PI/3); 		//rotate clockwise
		Transform3D moveRight = new Transform3D();
					moveRight.setTranslation(new Vector3f( 0.6f, .0f, .0f));//add the position to the transform object. Move LEFT(users left)
		Transform3D moveLeft = new Transform3D();
					moveLeft.setTranslation(new Vector3f( -.6f, 0.0f, 0.0f));//Move RIGHT(users right hand side)
		 
		rotateX.setScale(.5);
		moveRight.mul(rotateZCounterClk);	//combine move right and counter clockwise rotation
		moveLeft.mul(rotateZClkwise);		//combine move left and clockwise rotation
		
		//Transform Groups for each object
		TransformGroup spinGroup = new TransformGroup();
		TransformGroup firstBranchGrp = new TransformGroup();
		TransformGroup secondBranchGrp = new TransformGroup();
		TransformGroup rotateGroup = new TransformGroup(rotateX);
		
		firstBranchGrp.setTransform(moveRight);
		firstBranchGrp.addChild(cylinderBranch);
		
		secondBranchGrp.setTransform(moveLeft);
		secondBranchGrp.addChild(cylinderBranch2);
		
		spinGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spinGroup.addChild(cylinderBase);
		spinGroup.addChild(firstBranchGrp);
		spinGroup.addChild(secondBranchGrp);
		
		rotateGroup.addChild(spinGroup);
		
		//more or less the world that contains all the objects we will be creating
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(rotateGroup);
		
		//Continuous rotation of Tree
		spinGroup.addChild(createRotation(spinGroup));	
	
		return objRoot;
	}
	
	public RotationInterpolator createRotation(TransformGroup spinGroup){
		Alpha rotationAlpha = new Alpha(-1, 5000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, spinGroup);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		return rotator;
	}
	
	/**
	 * This method creates the light for the world so whatever objects are created can actually be seen
	 * @return
	 */
	public DirectionalLight createLight(){
		// Create a red light that shines for 100m from the origin
		Color3f light1Color = new Color3f(0.8f, 1.1f, 0.3f);//light color
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);//specifies where the light is coming from
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);//creates the light
		light1.setInfluencingBounds(bounds);
		
		return light1;
	}


}//end of Tree3D class