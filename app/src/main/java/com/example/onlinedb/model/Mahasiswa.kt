package com.example.onlinedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mahasiswa (
    val nim: String,
    val nama: String,

    @SerialName("jenis_kelamin")
    val jenisKelamin: String,

    val alamat: String,
    val kelas: String,
    val angkatan: String
)
