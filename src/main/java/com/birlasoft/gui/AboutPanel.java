package com.birlasoft.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class AboutPanel extends JPanel{
	public AboutPanel(String title)
	{
		this.title = title;
	}
@Override
public void paintComponent(Graphics g)
	{
Graphics2D g2d = (Graphics2D) g;
RenderingHints hints = new RenderingHints(null);
hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION ,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
hints.put(RenderingHints.KEY_ANTIALIASING ,RenderingHints.VALUE_ANTIALIAS_ON );
hints.put(RenderingHints.KEY_COLOR_RENDERING ,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
hints.put(RenderingHints.KEY_TEXT_ANTIALIASING ,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
g2d.setRenderingHints(hints);
Rectangle2D rect = new Rectangle2D.Double(0,0,getWidth(),getHeight());
g2d.setColor(Color.black);
g2d.fill(rect);
GeneralPath path = new GeneralPath();
/*int XCord[] = {10,200,75,356};
int YCord[] = {24,102,325,54};
g2d.setColor(Color.white);
putPixel(XCord,YCord,g2d);*/


int arcLength = 20;
int arcWidth = 20;

//BackCircle
int circleDiameter = 2*(arcLength);
Ellipse2D backCircle = new Ellipse2D.Double();
backCircle.setFrameFromCenter(getWidth()/2,getHeight()/2,getWidth()/2+arcLength,getHeight()/2-arcLength);
Color darkBlue = new Color(23,34,90);
Color lightBlue = new Color(23,36,150);
g2d.setPaint(new GradientPaint(getWidth()/2,400,darkBlue,getWidth()/2,getHeight(),lightBlue));
g2d.fill(backCircle);
//Transparent circle
Composite oldComposite = g2d.getComposite(); //Retrieve Original Composite
int sizingFactor = 10;
Ellipse2D frontCircle = new Ellipse2D.Double();
frontCircle.setFrameFromCenter(getWidth()/2,getHeight()/2,getWidth()/2+(arcLength-sizingFactor),getHeight()/2-(arcLength-sizingFactor));
int rule = AlphaComposite.SRC_OVER;
float alpha = 0.2f;
g2d.setComposite(AlphaComposite.getInstance(rule, alpha));
g2d.setColor(Color.white);
g2d.fill(frontCircle);
//Restore Original Composite
g2d.setComposite(oldComposite);
//Drawing Arc for birlasoft Logo
QuadCurve2D upperCurve = new QuadCurve2D.Double(getWidth()/2, getHeight()/2,getWidth()/2-arcWidth, getHeight()/2-arcLength/2,getWidth()/2,getHeight()/2-arcLength);
QuadCurve2D lowerCurve = new QuadCurve2D.Double(getWidth()/2, getHeight()/2,getWidth()/2-arcWidth/2, getHeight()/2-arcLength/2,getWidth()/2,getHeight()/2-arcLength);
path.moveTo(getWidth()/2, getHeight()/2);

/*path.curveTo(getWidth()/2-arcWidth, getHeight()/2-arcLength/3,getWidth()/2-arcWidth, getHeight()/2-(arcLength/3)* 2,getWidth()/2,getHeight()/2-arcLength);
path.curveTo(getWidth()/2-arcWidth/2, getHeight()/2+arcLength/3,getWidth()/2-arcWidth/2, getHeight()/2+(arcLength/3)* 2,getWidth()/2,getHeight()/2);*/


path.curveTo(getWidth()/2-arcWidth, getHeight()/2-arcLength/3,getWidth()/2-arcWidth, getHeight()/2-(arcLength/3)* 2,getWidth()/2,getHeight()/2-arcLength);
path.curveTo(getWidth()/2-arcWidth/2, getHeight()/2-arcLength + arcLength/3,getWidth()/2-arcWidth/2, getHeight()/2 - arcLength + (arcLength/3)* 2,getWidth()/2,getHeight()/2);


//path.append(upperCurve,false);
//path.append(lowerCurve,false);
g2d.setColor(Color.white);
for(int i=1; i<=4;i++)
{
//g2d.translate(getWidth()/2,getHeight()/2);
g2d.rotate(33,getWidth()/2,getHeight()/2);
g2d.setColor(i%2==0 ? Color.red.darker().darker(): Color.gray);
g2d.fill(path);
}

//The Circle with equidistance Points
g2d.setColor(Color.yellow);
Ellipse2D outerCircle = new Ellipse2D.Double();
outerCircle.setFrameFromCenter(getWidth()/2,getHeight()/2,getWidth()/2 + 150,getHeight()/2 - 150);
g2d.draw(outerCircle);
int radiusOuterCircle = 150;
g2d.drawString("With Regards, the Hot Docs team", getWidth()/2 -radiusOuterCircle, getHeight()/2+ radiusOuterCircle +15 );
int NEquiPoints = 11;
Point2D equiPoints[] = new Point2D.Double[NEquiPoints];
float rotateAngle = (float)360/NEquiPoints;

float angleTraveled = 0;
//AffineTransform originalTransform = g2d.getTransform();
g2d.translate(getWidth()/2,getHeight()/2);
for(int i=0;i<NEquiPoints;i++)
{
//equiPoints[i] = new Point2D.Double(getWidth()/2 + 240,getHeight()/2 - 240);
equiPoints[i] = new Point2D.Double(radiusOuterCircle * Math.cos(Math.toRadians(angleTraveled)),radiusOuterCircle*Math.sin(Math.toRadians(angleTraveled)));
angleTraveled +=rotateAngle;
//g2d.rotate(rotateAngle,getWidth()/2,getHeight()/2);
//g2d.transform(AffineTransform.getRotateInstance(rotateAngle,getWidth()/2,getHeight()/2));
}
g2d.translate(0,0);
//g2d.setTransform(originalTransform);

for(int i =0; i<NEquiPoints;i++)
{
//System.out.println(equiPoints[i]);
for(int j = 0;j<NEquiPoints;j++)
{
//g2d.rotate(rotateAngle,getWidth()/2,getHeight()/2);
g2d.draw(new Line2D.Double(equiPoints[i], equiPoints[j]));
}
}

 }
public void putPixel(int[] x, int[] y, Graphics2D g)
{
for(double t=0; t<1;t = t+0.0005)
{
Double x1 =(x[0]*Math.pow((1-t),3)+x[1]*3*t*Math.pow((1-t),2)+x[2]*3*t*t*(1-t)+x[3]*3*t*t*t);
Double y1 =(y[0]*Math.pow((1-t),3)+y[1]*3*t*Math.pow((1-t),2)+y[2]*3*t*t*(1-t)+y[3]*3*t*t*t);
g.fill(new Ellipse2D.Double(x1,y1,1,1));
}
}
private String title;
}
