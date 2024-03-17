package com.yrun.domain.premium

interface PremiumStorage {

    interface Read {

        fun havePremium(): Boolean
    }

    interface Save {

        fun savePremium()
    }

    interface Mutable : Read, Save
}