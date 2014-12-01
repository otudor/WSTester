<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fx="http://javafx.com/fxml">

    <xsl:template match="/">

        <xsl:processing-instruction name="import">
            java.lang.*
        </xsl:processing-instruction>

        <xsl:processing-instruction name="import">
            javafx.scene.layout.*
        </xsl:processing-instruction>

        <xsl:processing-instruction name="import">
            javafx.scene.control.*
        </xsl:processing-instruction>

        <xsl:processing-instruction name="import">
            javafx.geometry.Insets
        </xsl:processing-instruction>

        <xsl:processing-instruction name="import">
            javafx.collections.FXCollections
        </xsl:processing-instruction>

        <GridPane hgap="5" vgap="5" fx:id="form" fx:controller="FormController.java">         
            <columnConstraints>
                <ColumnConstraints halignment="RIGHT" hgrow="NEVER" />
                <ColumnConstraints halignment="LEFT" hgrow="ALWAYS" />
            </columnConstraints>
            <padding>
                <Insets top="10" bottom="10" left="10" right="10"/>
            </padding>

            <xsl:apply-templates select="//text"/>
            <xsl:apply-templates select="//question"/>

        </GridPane>

    </xsl:template>

    <xsl:template match="text">
        <Label text="{.}" wrapText="true" textAlignment="RIGHT"
            GridPane.columnIndex="0"
            GridPane.rowIndex="{count(../preceding-sibling::question)}" />  
    </xsl:template>


    <xsl:template name="controlCoords">
            <GridPane.columnIndex>1</GridPane.columnIndex>
            <GridPane.rowIndex>
                <xsl:value-of select="count(preceding-sibling::question)"/>
            </GridPane.rowIndex>    
    </xsl:template>

    <xsl:template match="question[@type='desc']">
        <TextArea fx:id="{@id}" id="{@id}">
            <xsl:call-template name="controlCoords" />
        </TextArea>     
    </xsl:template>

    <xsl:template match="question[@type='list']">
        <ComboBox fx:id="{@id}" id="{@id}">
            <xsl:call-template name="controlCoords" />
            <items>
                <FXCollections fx:factory="observableArrayList">
                    <xsl:for-each select="choices/choice">
                        <String fx:value="{.}"/>
                    </xsl:for-each>
                </FXCollections>
            </items>
        </ComboBox>
    </xsl:template>

    <xsl:template match="question[@type='value']">
        <TextField fx:id="{@id}" id="{@id}">
            <xsl:call-template name="controlCoords" />
        </TextField>    
    </xsl:template>

    <xsl:template match="question[@type='yesNo']">
        <CheckBox fx:id="{@id}" id="{@id}">
            <xsl:call-template name="controlCoords" />
        </CheckBox> 
    </xsl:template>

</xsl:stylesheet>