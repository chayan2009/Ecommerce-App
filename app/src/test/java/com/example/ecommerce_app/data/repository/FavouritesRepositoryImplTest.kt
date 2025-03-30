import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ecommerce_app.core.common.FavouriteMapper
import com.example.ecommerce_app.data.db.FavouriteDao
import com.example.ecommerce_app.data.repository.FavouritesRepositoryImpl
import com.example.ecommerce_app.data.source.dto.FavouriteEntity
import com.example.ecommerce_app.domain.model.Favourite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
class FavouritesRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FavouritesRepositoryImpl
    private lateinit var mockFavouriteDao: FavouriteDao

    private val testFavourite = Favourite(
        id = 1,
        title = "Test Product",
        price = 99.99,
        description = "Test description",
        category = "test",
        image = "test.jpg"
    )

    private val testFavouriteEntity = FavouriteEntity(
        id = 1,
        title = "Test Product",
        price = 99.99,
        description = "Test description",
        category = "test",
        image = "test.jpg"
    )

    @Before
    fun setUp() {
        mockFavouriteDao = mock(FavouriteDao::class.java)
        repository = FavouritesRepositoryImpl(mockFavouriteDao)
    }

    @Test
    fun getFavourites_shouldEmitEmptyList_whenNoFavouritesExist() = runTest {
        `when`(mockFavouriteDao.getFavourites()).thenReturn(flowOf(emptyList()))
        val result = repository.getFavourites().first()
        assertEquals(0, result.size)
        verify(mockFavouriteDao).getFavourites()
        verifyNoMoreInteractions(mockFavouriteDao)
    }

    @Test
    fun insertFavouritesItem_shouldConvertAndInsertDomainModelToDatabase() = runTest {
        val expectedEntity = FavouriteMapper.domainToEntity(testFavourite)
        repository.insertFavouritesItem(testFavourite)
        verify(mockFavouriteDao).insertFavouriteItem(expectedEntity)
        verifyNoMoreInteractions(mockFavouriteDao)
    }

    @Test
    fun deleteFavouritesItem_shouldCallDeleteWithCorrectID() = runTest {
        val favIdToDelete = testFavourite.id
        repository.deleteFavouritesItem(favIdToDelete)
        verify(mockFavouriteDao).deleteFavouriteItemById(favIdToDelete)
        verifyNoMoreInteractions(mockFavouriteDao)
    }
}