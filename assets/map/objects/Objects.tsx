<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.2" name="Objects" tilewidth="115" tileheight="161" tilecount="3" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="2" type="Object">
  <properties>
   <property name="animation" value="Idle"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlas" value="Player"/>
   <property name="atlasKey" value="player"/>
   <property name="controller" type="bool" value="true"/>
   <property name="player" type="bool" value="true"/>
   <property name="speed" type="float" value="3"/>
   <property name="z" type="int" value="2"/>
  </properties>
  <image source="player.png" width="32" height="32"/>
  <objectgroup draworder="index" id="2">
   <object id="8" x="10.6907" y="16.0361" width="9.54527" height="6.8726"/>
  </objectgroup>
 </tile>
 <tile id="3">
  <image source="greenhosue_built.png" width="115" height="161"/>
 </tile>
 <tile id="4" type="Prop">
  <properties>
   <property name="greenhouse" type="bool" value="true"/>
  </properties>
  <image source="greenhouse_broken.png" width="115" height="129"/>
  <objectgroup draworder="index" id="2">
   <object id="7" x="0" y="-1" width="115" height="130.5">
    <properties>
     <property name="greenhouse" type="bool" value="true"/>
    </properties>
   </object>
  </objectgroup>
 </tile>
</tileset>
