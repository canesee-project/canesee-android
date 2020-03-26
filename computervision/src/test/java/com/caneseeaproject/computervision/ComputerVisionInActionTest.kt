package com.caneseeaproject.computervision

import org.junit.Test


class ComputerVisionInActionTest {
    private val cvTranslatorTest = CVTranslator()
    private val cvTranslatorTest_OCR = """{"type":"1", 
          "value":"Hello!"}"""
    private val cvTranslatorTest_Scenery = """{"type":"2", 
          "value":"University"}"""
    private val cvTranslatorTest_Facial = """{"type":"3", 
          "value":"Pretty People"}"""
    private val cvTranslatorTest_Emotion = """{"type":"4", 
          "value":"Happy"}"""
    private val cvTranslatorTest_ObjectDetection = """{"type":"5", 
          "value": [
     {"label": "Human", "pos" :"in the middle"},
     {"label": "Bottle","pos": "on the right"},
     {"label": "table", "pos" :"on the right"}
]}"""



    @Test
    fun testTokenizeEmotion(){
        val tokenized = cvTranslatorTest.tokenize(cvTranslatorTest_Emotion)
        println(tokenized)
        assert(tokenized is Vision.Emotion && tokenized.emotion == "Happy")
    }

    @Test
    fun testTokenizeFacial(){
        val tokenized = cvTranslatorTest.tokenize(cvTranslatorTest_Facial)
        println(tokenized)
        assert(tokenized is Vision.Facial && tokenized.prettyFace == "Pretty People")
    }

    @Test
    fun testTokenizeScenery(){
        val tokenized = cvTranslatorTest.tokenize(cvTranslatorTest_Scenery)
        println(tokenized)
        assert(tokenized is Vision.Scenery && tokenized.scene == "University")
    }

    @Test
    fun testTokenizeObjects(){
        val tokenized = cvTranslatorTest.tokenize(cvTranslatorTest_ObjectDetection)
        println(tokenized)
        assert(tokenized is Vision.ObjectDetection && tokenized.objects.get(2) == DetectedObject("table" , "on the right"))
    }


    @Test
    fun testTokenizeOCR(){
        val tokenized = cvTranslatorTest.tokenize(cvTranslatorTest_OCR)
        println(tokenized.toString())
        assert(tokenized is Vision.OCR && tokenized.transcript == "Hello!")
    }
    @Test
    fun testPack(){
        val processed = cvTranslatorTest.pack(CVControl.ModeChange(2))
        println(processed)
        assert(processed == "0_2")
    }


}



