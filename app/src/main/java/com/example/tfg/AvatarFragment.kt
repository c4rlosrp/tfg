package com.example.tfg

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso


class AvatarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivTopClothes = view.findViewById<ImageView>(R.id.ivTopClothes)
        val ivBottomClothes = view.findViewById<ImageView>(R.id.ivBottomClothes)


        Picasso.get().load(MyGlobalValue.urlTop).into(ivTopClothes)
        Picasso.get().load(MyGlobalValue.urlBottom).into(ivBottomClothes)

        val infoButton = view.findViewById<ImageButton>(R.id.btnInfo)
        infoButton.setOnClickListener{
            val showPopUp = PopUpFragment()
            showPopUp.show((activity as AppCompatActivity).supportFragmentManager,"showPopUp")
        }

        val btnTopClothes = view.findViewById<ImageButton>(R.id.btnTopClothes)
        val btnBottomClothes = view.findViewById<ImageButton>(R.id.btnBottomClothes)

        btnTopClothes.setOnClickListener {
            val leftPopup = LeftPopupFragment()
            leftPopup.show((activity as AppCompatActivity).supportFragmentManager, "LeftPopup")
        }

        btnBottomClothes.setOnClickListener {
            val rightPopup = RightPopupFragment()
            rightPopup.show((activity as AppCompatActivity).supportFragmentManager, "RightPopup")
        }
    }

    fun reloadAvatar() {
        val ivTopClothes = view?.findViewById<ImageView>(R.id.ivTopClothes)
        Picasso.get().load(MyGlobalValue.urlTop).into(ivTopClothes)
    }

    fun reloadBottomClothes() {
        val ivBottomClothes = view?.findViewById<ImageView>(R.id.ivBottomClothes)
        Picasso.get().load(MyGlobalValue.urlBottom).into(ivBottomClothes)
    }

    class LeftPopupFragment : DialogFragment() {

        interface OnIndigoSelectedListener {
            fun onIndigoSelected()
        }

        private lateinit var indigoSelectedListener: OnIndigoSelectedListener

        override fun onAttach(context: Context) {
            super.onAttach(context)
            try {
                indigoSelectedListener = context as OnIndigoSelectedListener
            } catch (e: ClassCastException) {
                throw ClassCastException("$context must implement OnIndigoSelectedListener")
            }
        }

        @SuppressLint("MissingInflatedId")
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_left_popup, container, false)

            val ivIndigo = view.findViewById<ImageView>(R.id.ivIndigoTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Indigo_97d23544e5.png").into(ivIndigo)
            val ivSeaGreen = view.findViewById<ImageView>(R.id.ivSeaGreenTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Sea_Green_2470f92394.png").into(ivSeaGreen)
            val ivMistyRose = view.findViewById<ImageView>(R.id.ivMistyRoseTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700657421/Camiseta_Misty_Rose_926a44413c.png").into(ivMistyRose)
            val ivSalmon = view.findViewById<ImageView>(R.id.ivSalmonTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Dark_Salmon_89311037ff.png").into(ivSalmon)
            val ivBlack = view.findViewById<ImageView>(R.id.ivBlackTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Camiseta_Dark_Slate_Gray_8b48be1f0a.png").into(ivBlack)
            val ivBrown = view.findViewById<ImageView>(R.id.ivBrownTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Brown_d22c7606e8.png").into(ivBrown)
            val ivCornflowerBlue = view.findViewById<ImageView>(R.id.ivCornflowerBlueTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Cornflower_Blue_5a6a607453.png").into(ivCornflowerBlue)
            val ivGray = view.findViewById<ImageView>(R.id.ivGrayTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Light_Gray_1aa1ba123e.png").into(ivGray)
            val ivBeige = view.findViewById<ImageView>(R.id.ivBeigeTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Camiseta_Beige_4c8be27bb2.png").into(ivBeige)
            val ivLightSkyBlue = view.findViewById<ImageView>(R.id.ivLightSkyBlueTop)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656767/Camiseta_Light_Sky_Blue_acb99670c4.png").into(ivLightSkyBlue)

            val llIndigo = view.findViewById<LinearLayout>(R.id.llIndigoTop)
            llIndigo.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Indigo_97d23544e5.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llSeaGreen = view.findViewById<LinearLayout>(R.id.llSeaGreenTop)
            llSeaGreen.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Sea_Green_2470f92394.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llMistyRose = view.findViewById<LinearLayout>(R.id.llMistyRoseTop)
            llMistyRose.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700657421/Camiseta_Misty_Rose_926a44413c.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llSalmon = view.findViewById<LinearLayout>(R.id.llSalmonTop)
            llSalmon.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Dark_Salmon_89311037ff.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBlack = view.findViewById<LinearLayout>(R.id.llBlackTop)
            llBlack.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Camiseta_Dark_Slate_Gray_8b48be1f0a.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBrown = view.findViewById<LinearLayout>(R.id.llBrownTop)
            llBrown.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Brown_d22c7606e8.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llCornflowerBlue = view.findViewById<LinearLayout>(R.id.llCornflowerBlueTop)
            llCornflowerBlue.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Cornflower_Blue_5a6a607453.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llGray = view.findViewById<LinearLayout>(R.id.llGrayTop)
            llGray.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656766/Camiseta_Light_Gray_1aa1ba123e.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBeige = view.findViewById<LinearLayout>(R.id.llBeigeTop)
            llBeige.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Camiseta_Beige_4c8be27bb2.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llLightSkyBlue = view.findViewById<LinearLayout>(R.id.llLightSkyBlueTop)
            llLightSkyBlue.setOnClickListener {
                MyGlobalValue.urlTop = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656767/Camiseta_Light_Sky_Blue_acb99670c4.png"
                indigoSelectedListener.onIndigoSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }


            return view
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialog = super.onCreateDialog(savedInstanceState)

            // Establecer la posición del pop-up en el lado derecho
            dialog.window?.setGravity(Gravity.START)

            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationLeft // Agrega la animación
            return dialog
        }

    }

    class RightPopupFragment : DialogFragment() {

        interface OnIndigoBottomSelectedListener {
            fun onIndigoBottomSelected()
        }

        private lateinit var indigoSelectedListener: OnIndigoBottomSelectedListener

        override fun onAttach(context: Context) {
            super.onAttach(context)
            try {
                indigoSelectedListener = context as OnIndigoBottomSelectedListener
            } catch (e: ClassCastException) {
                throw ClassCastException("$context must implement OnIndigoSelectedListener")
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_right_popup, container, false)

            val ivIndigo = view.findViewById<ImageView>(R.id.ivIndigoBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Indigo_3c87049204.png").into(ivIndigo)
            val ivSeaGreen = view.findViewById<ImageView>(R.id.ivSeaGreenBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Sea_Green_be9ed19c59.png").into(ivSeaGreen)
            val ivMistyRose = view.findViewById<ImageView>(R.id.ivMistyRoseBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Misty_Rose_04372fb756.png").into(ivMistyRose)
            val ivSalmon = view.findViewById<ImageView>(R.id.ivSalmonBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Dark_Salmon_8abfc3f18a.png").into(ivSalmon)
            val ivBlack = view.findViewById<ImageView>(R.id.ivBlackBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Dark_Slate_Gray_72b4e057a6.png").into(ivBlack)
            val ivBrown = view.findViewById<ImageView>(R.id.ivBrownBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Brown_11b2f046ee.png").into(ivBrown)
            val ivCornflowerBlue = view.findViewById<ImageView>(R.id.ivCornflowerBlueBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Cornflower_Blue_07fe7bfe25.png").into(ivCornflowerBlue)
            val ivGray = view.findViewById<ImageView>(R.id.ivGrayBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Light_Gray_a8b08b41d5.png").into(ivGray)
            val ivBeige = view.findViewById<ImageView>(R.id.ivBeigeBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Beige_35522f1bac.png").into(ivBeige)
            val ivLightSkyBlue = view.findViewById<ImageView>(R.id.ivLightSkyBlueBottom)
            Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700657421/Pantalon_Light_Sky_Blue_3fb69bdebe.png").into(ivLightSkyBlue)

            val llIndigo = view.findViewById<LinearLayout>(R.id.llIndigoBottom)
            llIndigo.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Indigo_3c87049204.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llSeaGreen = view.findViewById<LinearLayout>(R.id.llSeaGreenBottom)
            llSeaGreen.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Sea_Green_be9ed19c59.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llMistyRose = view.findViewById<LinearLayout>(R.id.llMistyRoseBottom)
            llMistyRose.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Misty_Rose_04372fb756.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llSalmon = view.findViewById<LinearLayout>(R.id.llSalmonBottom)
            llSalmon.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Dark_Salmon_8abfc3f18a.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBlack = view.findViewById<LinearLayout>(R.id.llBlackBottom)
            llBlack.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Dark_Slate_Gray_72b4e057a6.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBrown = view.findViewById<LinearLayout>(R.id.llBrownBottom)
            llBrown.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656764/Pantalon_Brown_11b2f046ee.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llCornflowerBlue = view.findViewById<LinearLayout>(R.id.llCornflowerBlueBottom)
            llCornflowerBlue.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Cornflower_Blue_07fe7bfe25.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llGray = view.findViewById<LinearLayout>(R.id.llGrayBottom)
            llGray.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Light_Gray_a8b08b41d5.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llBeige = view.findViewById<LinearLayout>(R.id.llBeigeBottom)
            llBeige.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700656765/Pantalon_Beige_35522f1bac.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }
            val llLightSkyBlue = view.findViewById<LinearLayout>(R.id.llLightSkyBlueBottom)
            llLightSkyBlue.setOnClickListener {
                MyGlobalValue.urlBottom = "https://res.cloudinary.com/dpfkjvqn5/image/upload/v1700657421/Pantalon_Light_Sky_Blue_3fb69bdebe.png"
                indigoSelectedListener.onIndigoBottomSelected()
                dismiss() // Cerrar el popup después de seleccionar Indigo
            }


            return view
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialog = super.onCreateDialog(savedInstanceState)

            // Establecer la posición del pop-up en el lado derecho
            dialog.window?.setGravity(Gravity.END)

            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation // Agrega la animación
            return dialog
        }

    }

}