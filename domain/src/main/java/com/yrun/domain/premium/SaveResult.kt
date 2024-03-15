package com.yrun.domain.premium

interface SaveResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccessful()

        fun mapRequirePremium()
    }

    object Success : SaveResult {

        override fun map(mapper: Mapper) {
            mapper.mapSuccessful()
        }
    }

    object RequireSave : SaveResult {

        override fun map(mapper: Mapper) {
            mapper.mapRequirePremium()
        }
    }
}