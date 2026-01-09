package ch.cnpv.gui2.data

import ch.cnpv.gui2.models.Profil

object ProfilsData {
    val allProfils = listOf(
        Profil(
            id = "11111111-1111-1111-1111-111111111111",
            name = "Alice",
            imageUrl = "http://10.0.2.2:8000/storage/profils/alice.jpg",
            hash = null,
            createdAt = "2024-10-01T00:00:00.000Z",
            updatedAt = "2024-10-01T00:00:00.000Z"
        ),
        Profil(
            id = "22222222-2222-2222-2222-222222222222",
            name = "Bob",
            imageUrl = "http://10.0.2.2:8000/storage/profils/bob.jpg",
            hash = null,
            createdAt = "2024-10-02T00:00:00.000Z",
            updatedAt = "2024-10-02T00:00:00.000Z"
        ),
        Profil(
            id = "33333333-3333-3333-3333-333333333333",
            name = "Charlie",
            imageUrl = "http://10.0.2.2:8000/storage/profils/charlie.jpg",
            hash = null,
            createdAt = "2024-10-03T00:00:00.000Z",
            updatedAt = "2024-10-03T00:00:00.000Z"
        ),
        Profil(
            id = "44444444-4444-4444-4444-444444444444",
            name = "Didié",
            imageUrl = "http://10.0.2.2:8000/storage/profils/didie.jpg",
            hash = null,
            createdAt = "2024-10-04T00:00:00.000Z",
            updatedAt = "2024-10-04T00:00:00.000Z"
        ),
        Profil(
            id = "55555555-5555-5555-5555-555555555555",
            name = "Diana",
            imageUrl = "http://10.0.2.2:8000/storage/profils/diana.jpg",
            hash = null,
            createdAt = "2024-10-05T00:00:00.000Z",
            updatedAt = "2024-10-05T00:00:00.000Z"
        )
    )

    fun getProfilById(id: String): Profil? {
        return allProfils.find { it.id == id }
    }

    fun getDefaultProfil(): Profil {
        return allProfils.first()
    }
}