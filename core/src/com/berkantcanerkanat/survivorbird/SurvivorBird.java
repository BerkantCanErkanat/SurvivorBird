package com.berkantcanerkanat.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
import java.util.logging.Handler;

import javax.xml.soap.Text;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;// image icin falan hep texture objesi olustur contructorına .png yi at create() te
    Texture bird;
    ShapeRenderer shapeRenderer;// hazır bulunan shapelerı cızmek ıcın begin ve end kullanılır yıne
    Bee [] bees1 = new Bee[3];
	Bee [] bees2 = new Bee[3];
	Bee [] bees3 = new Bee[3];
	Bee [] bees4 = new Bee[3];
	float [] konum = new float[4];
    int birdx;
    float birdy;
    float velocity = 0.0f;
    float gravity = 0.4f;
    int gameState = 0;
    boolean beepic = false;
    boolean [] gecti = new boolean[4];
	int count = 0;
	int skor = 0;
	float x4;
	float y4;
	float x1;
	float x2;
	float y1;
	float y2;
	float x3;
	float y3;
	Texture beeTexture;
	Texture playB;
	float anlik;
	BitmapFont font;
	BitmapFont font2;
	Rectangle rect;
	int touchedx=0,touchedy=0;
	boolean playButton = false;
	int resimsayac=0;
	@Override// bir defa calısıcak ve initialize kısımları burada
	public void create () {
		rect = new com.badlogic.gdx.math.Rectangle(Gdx.graphics.getWidth()/3+Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/4+Gdx.graphics.getWidth()/18,Gdx.graphics.getWidth()/6+Gdx.graphics.getWidth()/25,Gdx.graphics.getHeight()/6);
		font = new BitmapFont();
		playB = new Texture("pb.png");
		font.setColor(Color.WHITE);
		font.getData().setScale(8);
		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(5);
     batch = new SpriteBatch();
     background = new Texture("background.png");
     bird = new Texture("bird.png");
     beeTexture = new Texture("bee1.png");
     shapeRenderer = new ShapeRenderer();
     initialize();
	}
	public void initialize(){
		birdx = Gdx.graphics.getWidth()/3;
		birdy = Gdx.graphics.getHeight()/2;
		x1= Gdx.graphics.getWidth();
		x2 = x1+Gdx.graphics.getWidth()/3;
		x3 = x2+Gdx.graphics.getWidth()/3;
		x4 = x3+Gdx.graphics.getWidth()/2;
		y1 = random();
		y2 = random();
		y3 = random();
		y4 = random();
		//shapeRenderer = new ShapeRenderer();
		for(int i = 0 ;i<3;i++){
			bees1[i] = new Bee(x1,y1);
			y1 -= Gdx.graphics.getHeight()/10;
		}
		anlik = bees1[0].x;
		for(int i = 0 ;i<3;i++){
			bees2[i] = new Bee(x2,y2);
			y2 -= Gdx.graphics.getHeight()/10;
		}
		for(int i = 0 ;i<3;i++){
			bees3[i] = new Bee(x3,y3);
			y3 -= Gdx.graphics.getHeight()/10;
		}
		for(int i = 0 ;i<3;i++){
			bees4[i] = new Bee(x4,y4);
			y4 -= Gdx.graphics.getHeight()/10;
		}
	}
	public float random(){
		float y = new Random().nextFloat()*Gdx.graphics.getHeight();
		if(y > Gdx.graphics.getHeight()/2){
			return y - Gdx.graphics.getHeight()/3;
		}else{
			return y + Gdx.graphics.getHeight()/3;
		}
	}

	@Override// devamlı calısıcak method. kus ucması falan burada olur
	public void render (){

		//System.out.println(gameState);
		// bunların ustune gravity gibi methodlar yazabılırsın.
		if(gameState == 1){
			collisionControl();
			changeBeePic();
			skorKontrol();
			ekranKontrolu();
			for(Bee bee: bees1){
				bee.x -= 6;
			}
			for(Bee bee: bees2){
				bee.x -= 6;
			}
			for(Bee bee: bees3){
				bee.x -= 6;
			}
			for(Bee bee: bees4) {
				bee.x -= 6;
			}
				if(Gdx.input.justTouched()){
				changePic();
				if(birdy < Gdx.graphics.getHeight()*9/10)
				velocity=-9;
			}
			  if( birdy > 80 || velocity<0){
				  velocity += gravity;
				  birdy -= velocity;
			  }
		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				touchedx = Gdx.input.getX();
				touchedy = Gdx.graphics.getHeight()-Gdx.input.getY();
				playButtonCheck();
			}
		}else if(gameState == 2){
			if(Gdx.input.justTouched()){
				skor = 0;
				gecti[0] = false;
				gecti[1] = false;
				gecti[2] = false;
				gecti[3] = false;
				velocity = 0;
				initialize();
				gameState = 0;
			}
		}
        // bunların arasına ne cizecegin hangi objelerin olacagı yazılır
		batch.begin();
        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15,Gdx.graphics.getWidth()/15);
       if (gameState == 0) batch.draw(playB,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/4,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
		for(Bee bee: bees1){
			batch.draw(beeTexture,bee.x,bee.y,bee.en,bee.boy);
		}
		for(Bee bee: bees2){
			batch.draw(beeTexture,bee.x,bee.y,bee.en,bee.boy);
		}
		for(Bee bee: bees3){
			batch.draw(beeTexture,bee.x,bee.y,bee.en,bee.boy);
		}
		for(Bee bee: bees4){
			batch.draw(beeTexture,bee.x,bee.y,bee.en,bee.boy);
		}
		font.draw(batch,String.valueOf(skor),100,200);
		//if(gameState == 0) font2.draw(batch,"Tap To Play",100,Gdx.graphics.getHeight()/2);
		if(gameState == 2) font2.draw(batch,"Game Over Tap To Play Again",100,Gdx.graphics.getHeight()/2);
		batch.end();

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		if(playButton)shapeRenderer.rect(touchedx,touchedy,Gdx.graphics.getWidth()/50,Gdx.graphics.getWidth()/50);
		shapeRenderer.end();*/
	}
	public void playButtonCheck(){
         Rectangle rect1 = new Rectangle(touchedx,touchedy,Gdx.graphics.getWidth()/50,Gdx.graphics.getWidth()/50);
         if(Intersector.overlaps(rect1,rect)){
         	gameState = 1;
		 }
	}
	public void skorKontrol(){
		if(gecti[0] == false && bees1[0].x < birdx){
			gecti[0] = true;
			skor++;
		}
		if(gecti[1] == false && bees2[0].x < birdx){
			gecti[1] = true;
			skor++;
		}
		if(gecti[2] == false && bees3[0].x < birdx){
			gecti[2] = true;
			skor++;
		}
		if(gecti[3] == false && bees4[0].x < birdx){
			gecti[3] = true;
			skor++;
		}
	}

	public void collisionControl(){
		if(birdy <= 80) {
			gameState = 2;
			return;
		}
		Circle circle = new Circle(birdx+Gdx.graphics.getHeight()/15,birdy+Gdx.graphics.getHeight()/20,Gdx.graphics.getHeight()/23);
		for(Bee bee: bees1){
			if(Intersector.overlaps(circle,new Circle(bee.x+Gdx.graphics.getHeight()/20,bee.y+Gdx.graphics.getHeight()/20,Gdx.graphics.getHeight()/23))){
				gameState = 2;
				return;
			}
		}
		for(Bee bee: bees2){
			if(Intersector.overlaps(circle,new Circle(bee.x+Gdx.graphics.getHeight()/20,bee.y+Gdx.graphics.getHeight()/20,Gdx.graphics.getHeight()/23))){
				gameState = 2;
				return;
			}
		}
		for(Bee bee: bees3){
			if(Intersector.overlaps(circle,new Circle(bee.x+Gdx.graphics.getHeight()/20,bee.y+Gdx.graphics.getHeight()/20,Gdx.graphics.getHeight()/23))){
				gameState = 2;
				return;
			}
		}
		for(Bee bee: bees4){
			if(Intersector.overlaps(circle,new Circle(bee.x+Gdx.graphics.getHeight()/20,bee.y+Gdx.graphics.getHeight()/20,Gdx.graphics.getHeight()/23))){
				gameState = 2;
				return;
			}
		}
	}

	public void ekranKontrolu(){
		float y = random();
		if(bees1[0].x < 0 ){
			gecti[0] = false;
			for(int i = 0 ;i<3;i++){
				bees1[i].x = x1;
				bees1[i].y = y;
				y-= Gdx.graphics.getHeight()/10;
			}
		}
		if(bees2[0].x < 0 ){
			gecti[1] = false;
			for(int i = 0 ;i<3;i++){
				bees2[i].x = x1;
				bees2[i].y = y;
				y-= Gdx.graphics.getHeight()/10;
			}
		}
		if(bees3[0].x < 0 ){
			gecti[2] = false;
			for(int i = 0 ;i<3;i++){
				bees3[i].x = x1;
				bees3[i].y = y;
				y-= Gdx.graphics.getHeight()/10;
			}
		}
		if(bees4[0].x < 0 ){
			gecti[3] = false;
			for(int i = 0 ;i<3;i++){
				bees4[i].x = x1;
				bees4[i].y = y;
				y-= Gdx.graphics.getHeight()/10;
			}
		}

	}

	public void changeBeePic(){

        if(Math.abs(anlik-bees1[0].x) > 20){
        	anlik = bees1[0].x;
        	if(beepic == false) {
				beeTexture = new Texture("bee4.png");
				beepic = true;
			}else{
				beeTexture = new Texture("bee1.png");
				beepic = false;
			}
		}


	}
    public void changePic(){
		if(count == 0){
			bird = new Texture("bird.png");
			count=1;
		}else{
			bird = new Texture("birdd.png");
			count=0;
		}
	}
	
	@Override
	public void dispose () {

	}
}
