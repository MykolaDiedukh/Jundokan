package jundokan.view;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

class ModifButtonUI extends MetalButtonUI {

    private static final ModifButtonUI buttonUI = new ModifButtonUI();

    ModifButtonUI() {
    }

    public static ComponentUI createUI(JComponent c) {
        return new ModifButtonUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        final Color color1 = new Color(230, 255, 255, 0);
        final Color color2 = new Color(255, 230, 255, 64);
        final Color alphaColor = new Color(200, 200, 230, 64);
        final Color color3 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 0);
        final Color color4 = new Color(
            alphaColor.getRed(), alphaColor.getGreen(), alphaColor.getBlue(), 64);
        super.paint(g, c);
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint gradient1 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color1, 0.0F, 0.0F, color2);
        Rectangle rec1 = new Rectangle(0, 0, c.getWidth(), c.getHeight() / 2);
        g2D.setPaint(gradient1);
        g2D.fill(rec1);
        GradientPaint gradient2 = new GradientPaint(
            0.0F, (float) c.getHeight() / (float) 2, color3, 0.0F, c.getHeight(), color4);
        Rectangle rec2 = new Rectangle(0, c.getHeight() / 2, c.getWidth(), c.getHeight());
        g2D.setPaint(gradient2);
        g2D.fill(rec2);
    }

    @Override
    public void paintButtonPressed(Graphics g, AbstractButton b) {
        paintText(g, b, b.getBounds(), b.getText());
        g.setColor(Color.red.brighter());
        g.fillRect(0, 0, b.getSize().width, b.getSize().height);
    }

    public void paintBorder(Graphics g) {
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b,
        Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    }
}