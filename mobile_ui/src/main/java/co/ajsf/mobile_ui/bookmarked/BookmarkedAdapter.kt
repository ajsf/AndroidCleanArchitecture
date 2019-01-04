package co.ajsf.mobile_ui.bookmarked

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.ajsf.mobile_ui.R
import co.ajsf.mobile_ui.model.Project
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_bookmarked_project.view.*
import javax.inject.Inject

class BookmarkedAdapter @Inject constructor() : RecyclerView.Adapter<BookmarkedAdapter.ViewHolder>() {

    var projects: List<Project> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_bookmarked_project, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]

        holder.apply {
            ownerNameText.text = project.ownerName
            projectNameText.text = project.fullName
            Glide.with(itemView.context)
                .load(project.ownerAvatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImage)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarImage: ImageView = view.image_owner_avatar
        val ownerNameText: TextView = view.text_owner_name
        val projectNameText: TextView = view.text_project_name
    }
}