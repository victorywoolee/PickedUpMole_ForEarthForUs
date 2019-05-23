package app.woovictory.forearthforus.view.article.viewholder

import androidx.recyclerview.widget.RecyclerView
import app.woovictory.forearthforus.databinding.ItemListArticleDetailBinding
import app.woovictory.forearthforus.model.article.detail.ArticleDetailResponse

/**
 * Created by VictoryWoo
 */
class ArticleDetailViewHolder(
    private val binding: ItemListArticleDetailBinding,
    private val articleDetailImageClickListener: ((Int) -> Unit)?,
    private val articleDetailLikeClickListener: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ArticleDetailResponse, position: Int) {
        binding.articleDetailItems = item
        binding.executePendingBindings()

        // image click
        binding.itemArticleDetailImage.setOnClickListener {
            articleDetailImageClickListener?.invoke(position)
        }

        // like click
        binding.itemArticleDetailLikeButton.setOnClickListener {
            articleDetailLikeClickListener?.invoke(position)
        }
    }
}