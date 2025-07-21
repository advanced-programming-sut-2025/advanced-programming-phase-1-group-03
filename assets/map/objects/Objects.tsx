<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.2" name="Objects" tilewidth="32" tileheight="32" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="2" type="Object">
  <properties>
   <property name="animation" value="Idle"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlas" value="Player"/>
   <property name="atlasKey" value="player"/>
   <property name="controller" type="bool" value="true"/>
   <property name="speed" type="float" value="2"/>
   <property name="z" type="int" value="1"/>
  </properties>
  <image source="player.png" width="32" height="32"/>
  <objectgroup draworder="index" id="2">
   <object id="8" x="10.6907" y="16.0361" width="9.54527" height="6.8726"/>
  </objectgroup>
 </tile>
</tileset>
