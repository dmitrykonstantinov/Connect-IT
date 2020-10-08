package breakingumbrella.connectit.presentation.main

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import breakingumbrella.connectit.R
import breakingumbrella.connectit.entity.campaign.Level


class LevelsAdapter(activity: Activity,
                    private val myDataset: List<Level>,
                    private val playNowPosition: Int,
                    private val onLvlClick: (position: Int) -> Unit) : RecyclerView.Adapter<LevelsAdapter.ViewHolder>() {

    var colorYellow: Int
    var colorOrange: Int
    var colorWhiteTransparent: Int
    var animator: AnimatorSet

    init {
        colorYellow = activity.resources.getColor(R.color.mainYellow)
        colorOrange = activity.resources.getColor(R.color.mainOrange)
        colorWhiteTransparent = activity.resources.getColor(R.color.mainWhiteHalfTransparent)
        animator = AnimatorInflater.loadAnimator(activity, R.animator.highlight_current_lvl) as AnimatorSet
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.lvlDescTxt).text = (position + 1).toString()
        if (myDataset.get(position).isCompleted) {
            holder.view.findViewById<ImageView>(R.id.lvlDescImg).setColorFilter(colorYellow)
        } else {
            holder.view.findViewById<ImageView>(R.id.lvlDescImg).setColorFilter(colorWhiteTransparent)
        }
        if (position == playNowPosition) {
            holder.view.findViewById<ImageView>(R.id.lvlDescImg).setColorFilter(colorOrange)
            holder.view.setOnClickListener { onLvlClick.invoke(position) }
            animator.setTarget(holder.view) // set the view you want to animate
            animator.start()
        }
    }

}