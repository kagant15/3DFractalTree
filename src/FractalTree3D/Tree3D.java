package FractalTree3D;

import java.applet.Applet;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Cylinder;

@SuppressWarnings("serial")
public class Tree3D extends Applet{

	BranchGroup branches;
	TransformGroup branchTranGroup;
	SimpleUniverse universe;
	
	public static void main(String[] args) {
		new Tree3D(); 
	}

	public Tree3D() {
		
		universe = new SimpleUniverse();
		branches = new BranchGroup();
		branchTranGroup = new TransformGroup();
		branchTranGroup.addChild(createBase());
		branchTranGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//fractal recursive algorithm
		makeTree(4, 4, 0, 0.6, 0.05f);

		//rotation
		branchTranGroup.addChild(createRotation(branchTranGroup));
		
		branches.addChild(branchTranGroup);
		
		// adds light to the world
		branches.addChild(createLight());
		
		// look towards the tree
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(branches);
	
	}
	
	private void makeTree(int B, int level, double x1, double y1, float radius){
		if(level==0){
    		return;
    	}
    		
    	double theta=Math.PI/B;
    	float length=level*radius*5;
//    	double alpha=Math.PI*2/B;
		
    	for(int branchNum=0; branchNum<=B-1; branchNum++){
    		
    		double beta = theta*branchNum;
    		double   x2 = x1+length*Math.cos(Math.random()*theta+beta);
    		double   y2 = y1-length*Math.sin(Math.random()*theta+beta);
    		double midX = (x2+x1)/2;
       	 	double midY = (y2+y1)/2;
       	 	
       	 	//calculates the length of the cylinder using the distance formula
       	 	float height = (float) Math.sqrt( Math.pow( (x1-x2), 2 ) + Math.pow( (y1-y2), 2 ) );
       	 	
       	 	//THE FOLLOWING CODE IS FOR THE Y ROTATION
       	 	
       	 	//the new point of the base of the branch using point, distance, angle
//       	 	double TommyX = x1+((x2-x1)*Math.cos(alpha));
//       	 	double TommyY = y2+((x2-x1)*Math.sin(alpha));
//       	 	
//       	 	//rotate Y
//       	 	Transform3D rotateY = new Transform3D();
//       	 				rotateY.rotX(alpha);
       	 	
       	 	
       	 	//makes the cylinder
    		Cylinder branch= new Cylinder(radius, height);
       	 
       	 	//rotate --- calculates the angle of rotation
       	 	double angle = Math.PI/2 - Math.atan( (y2-y1) / (x2-x1) );
       	 	Transform3D rotation = new Transform3D();
       	 				rotation.rotZ(angle); 		
       	 	
    		//move --- calculates the location of the cylinder origin //must multiply by -1 because 3D coordinate system is y is positive going down 
    		Vector3f vector = new Vector3f((float) midX, (float) (-1*midY), 0);
    		Transform3D move = new Transform3D();
    					move.setTranslation(vector);
    		
    		//combines the move with the rotate			
    		move.mul(rotation);
    		//add the y rotation
//    		move.mul(rotateY);
    					
    		//Adds the new position and the branch to the same group
    		TransformGroup branchGrp = new TransformGroup();
    		    		   branchGrp.setTransform(move);
    		    		   branchGrp.addChild(branch);
    		    	
    		branchTranGroup.addChild(branchGrp);    		   
	
    		makeTree(B, level-1, x2, y2, radius-.018f);
    	}
    	
		
	}
	
	private TransformGroup createBase(){
		Cylinder cylinderBase = new Cylinder(0.05f, .80f);
		
		Transform3D rotateY = new Transform3D();
					rotateY.rotY(Math.PI/6);
					
		Transform3D rotateZ = new Transform3D();
					rotateZ.rotZ(Math.PI/4);
					
		Transform3D rotateX = new Transform3D();
					rotateX.rotX(Math.PI/4);
		//move
		Transform3D move= new Transform3D();
					move.setTranslation(new Vector3f( 0.0f, -0.95f, 0.0f));
//					move.mul(rotateX);
//					move.mul(rotateY);
//					move.mul(rotateZ);
		
		TransformGroup grp = new TransformGroup();
					   grp.setTransform(move);
					   grp.addChild(cylinderBase);  
		
		return grp;
		
	}
	
	/**
	 * ----THIS IS AN EXAMPLE METHOD TO CREATE A ROTATING TREE. DELETE WHEN COMPLETE---- 
	 * @return
	 */
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
	
		spinGroup.addChild(cylinderBase);
		spinGroup.addChild(firstBranchGrp);
		spinGroup.addChild(secondBranchGrp);
		
		rotateGroup.addChild(spinGroup);
		
		//Continuous rotation of Tree
		spinGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spinGroup.addChild(createRotation(spinGroup));	
		
		//more or less the world that contains all the objects we will be creating
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(rotateGroup);
		

	
		return objRoot;
	}
	
	public RotationInterpolator createRotation(TransformGroup spinGroup){
		Alpha rotationAlpha = new Alpha(-1, 10000);
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