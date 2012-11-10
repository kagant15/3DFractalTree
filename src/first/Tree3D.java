package first;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Cylinder;

public class Tree3D {

	float height;
	float radius;	
	
	public static void main(String[] args) { 
		new Tree3D(); 
	}

	public Tree3D() {
		
		// Create the universe
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup scene = createSceneGraph();
		scene.addChild(createLight());//adds the light to the group

		// look towards the tree
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);	
	}
	
	public BranchGroup createSceneGraph(){
		//The cylinders we've created with float radious and float height
		Cylinder cylinderBase = new Cylinder(0.4f, 1.5f);
		Cylinder cylinderBranch = new Cylinder(0.2f, 1.2f);
		Cylinder cylinderBranch2 = new Cylinder(0.2f, 1.2f);

		Transform3D rotateX = new Transform3D();			//Rotation for branch
					rotateX.rotX(Math.PI/8);
		Transform3D rotateY = new Transform3D();			//Rotation for other branch
					rotateY.rotY(Math.PI/3);		
		Transform3D rotateZCounterClk = new Transform3D();
					rotateZCounterClk.rotZ(Math.PI/-3);		//rotate counter clockwise
		Transform3D rotateZClkwise = new Transform3D();
					rotateZClkwise.rotZ(Math.PI/3); 		//rotate clockwise
		Transform3D moveRight = new Transform3D();
					moveRight.setTranslation(new Vector3f( 0.6f, .0f, .0f));//add the position to the transform object. Move RIGHT
		Transform3D moveLeft = new Transform3D();
					moveLeft.setTranslation(new Vector3f( -0.6f, .5f, .0f));//Move LEFT
		 
		rotateX.mul(rotateY);				//combine the X and Y rotation
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
		Alpha rotationAlpha = new Alpha(-1, 5000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, spinGroup);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		spinGroup.addChild(rotator);
		
	
		return objRoot;
	}
	
	/**
	 * This method creates the light for the world so whatever objects are created can actually be seen
	 * @return
	 */
	public DirectionalLight createLight(){
		// Create a red light that shines for 100m from the origin
		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);//light color
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);//specifies where the light is coming from
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);//creates the light\
		light1.setInfluencingBounds(bounds);
		
		return light1;
	}


}//end of Ball class