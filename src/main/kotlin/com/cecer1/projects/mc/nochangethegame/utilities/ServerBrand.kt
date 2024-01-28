package com.cecer1.projects.mc.nochangethegame.utilities

class ServerBrand {
    var isHypixel = false
        private set
    
    var isHypixelSMP = false
        private set
    
    fun setBrand(brand: String?) {
        if (brand == null) {
            isHypixel = false
            isHypixelSMP = false
        } else {
            isHypixel = brand.startsWith("Hypixel BungeeCord")
            if (isHypixel) {
                isHypixelSMP = brand.contains(" <- Paper")
            }
        }
    }
}
