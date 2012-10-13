package first;

import java.awt.event.MouseListener;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cylinder;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Square{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Square();

	}
	
	public Square(){
		
System.out.println("Hello!");		
		
		SimpleUniverse universe = new SimpleUniverse();
//		BranchGroup group = new BranchGroup();
//		Cylinder cylinder = new Cylinder(.5f, .9f);
//		
//		
//		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
//		   
//		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//
//		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
//
//		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
//
//		light1.setInfluencingBounds(bounds);
//
//		group.addChild(light1);
//		
//		group.addChild(cylinder);
//		universe.getViewingPlatform().setNominalViewingTransform();
//		universe.addBranchGraph(group);
	}

}
