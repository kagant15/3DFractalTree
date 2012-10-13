package first;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Cylinder;


public class Tree3D {
	
	public static void main(String[] args) { 
		new Tree3D(); 	
	}

	public Tree3D() {
		// Create the universe
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup scene = createSceneGraph();
		scene.addChild(createLight());//adds the light to the group

		// look towards the ball
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph(){
		BranchGroup objRoot = new BranchGroup();
		
		Cylinder cylinderBranch = new Cylinder(0.2f, 1.2f);
		Cylinder cylinderBase = new Cylinder(0.4f, 1.5f);
		Cylinder cylinderBranch2 = new Cylinder(0.2f, 1.2f);

		Transform3D rotateX = new Transform3D();
		Transform3D rotateY = new Transform3D();
		Transform3D rotateZ = new Transform3D();
		Transform3D rotateZ2 = new Transform3D();
		Transform3D move = new Transform3D();
		Transform3D move2 = new Transform3D();
		
		rotateX.rotX(Math.PI/8);
		rotateY.rotY(Math.PI/3);
		rotateZ.rotZ(Math.PI/-3);
		rotateZ2.rotZ(Math.PI/3); 
		move.setTranslation(new Vector3f( 0.6f, .0f, .0f));//add the position to the transform object
		move2.setTranslation(new Vector3f( -0.6f, .5f, .0f));
		 
		rotateX.mul(rotateY);
		move.mul(rotateZ);
		move2.mul(rotateZ2);
		
		TransformGroup rotateGroup = new TransformGroup(rotateX);
		TransformGroup spinGroup = new TransformGroup();
		TransformGroup firstBranch = new TransformGroup();
		TransformGroup secondBranch = new TransformGroup();
		
		firstBranch.setTransform(move);
		firstBranch.addChild(cylinderBranch);
		secondBranch.setTransform(move2);
		secondBranch.addChild(cylinderBranch2);
		spinGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spinGroup.addChild(cylinderBase);
		spinGroup.addChild(firstBranch);
		spinGroup.addChild(secondBranch);
		rotateGroup.addChild(spinGroup);
		objRoot.addChild(rotateGroup);
			
		Alpha rotationAlpha = new Alpha(-1, 5000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, spinGroup);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		spinGroup.addChild(rotator);
		
		return objRoot;
	}
	
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