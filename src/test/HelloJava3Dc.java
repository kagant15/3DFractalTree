package test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class HelloJava3Dc extends Applet{
	
	public static void main(String[] args) {
		new HelloJava3Dc();
	}

	public HelloJava3Dc(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		
		BranchGroup scene = createSceneGraph();
		scene.compile();
		
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		
		// This moves the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		simpleU.getViewingPlatform().setNominalViewingTransform();
		
		simpleU.addBranchGraph(scene);
	}
	
	public BranchGroup createSceneGraph(){
		BranchGroup objRoot = new BranchGroup();
		Transform3D rotateX = new Transform3D();
		Transform3D rotateY = new Transform3D();
		rotateX.rotX(Math.PI/3);
		rotateY.rotY(Math.PI/5);
		rotateX.mul(rotateY);	
		TransformGroup rotateGroup = new TransformGroup(rotateX);
		TransformGroup spinGroup = new TransformGroup();
		spinGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spinGroup.addChild(new ColorCube(0.4));
		rotateGroup.addChild(spinGroup);
		objRoot.addChild(rotateGroup);
			
		Alpha rotationAlpha = new Alpha(-1, 4000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, spinGroup);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		spinGroup.addChild(rotator);
		
		return objRoot;
	}
}
