(ns playground.keyboard
  (:import java.lang.System)
  (:import javax.swing.UIManager)
  (:import ch.randelshofer.quaqua.QuaquaManager)
  (:import [java.awt.event KeyListener WindowAdapter ActionListener KeyEvent])
  (:import javax.swing.JFrame)
  (:import java.awt.Dimension)
  (:use overtone.core)
  (:use overtone.inst.piano)
  )



(def ^{:dynamic true} key-notes
  {
   KeyEvent/VK_Z :C4
   KeyEvent/VK_S :C#4
   KeyEvent/VK_X :D4
   KeyEvent/VK_D :D#4
   KeyEvent/VK_C :E4
   KeyEvent/VK_V :F4
   KeyEvent/VK_G :F#4
   KeyEvent/VK_B :G4
   KeyEvent/VK_H :G#4
   KeyEvent/VK_N :A4
   KeyEvent/VK_J :A#4
   KeyEvent/VK_M :B4
   KeyEvent/VK_COMMA :C5
   KeyEvent/VK_L :C#5
   KeyEvent/VK_PERIOD :D5
   KeyEvent/VK_SEMICOLON :D#5
   KeyEvent/VK_SLASH :E5
   

   })

(defn event-to-note [ev]
  (get key-notes (.getKeyCode ev))
  )



(defn set-up-quaqua []
  (System/setProperty "Quaqua.tabLayoutPolicy" "wrap")
  (try
    (UIManager/setLookAndFeel (QuaquaManager/getLookAndFeel))
    (catch Exception e
      (println "Error setting Quaqua look&feel" e)))
  )


(defn make-keyboard [& props]
  (let [props (into {} (partition 2 props))]
    (let [frame (JFrame. (get props :title "Unnamed"))]
      (doto frame
        (.setSize (Dimension. 500 300))
        (.setVisible true)
        (.addKeyListener
         (proxy [WindowAdapter KeyListener] []
           (keyPressed [event]
             (if-let [n (event-to-note event)]
               (piano (note n)))
;             (println "Pressed " event)
             )
           
           (keyReleased [event]
             )
           
           ))
        (.toFront)
        )
      
      )

   
    
    )
  )

