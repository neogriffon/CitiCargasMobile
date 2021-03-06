package com.evo.citicargasmobile;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.evo.citicargasmobile.DAO.TransportadorRepository;
import com.evo.citicargasmobile.Entity.Transportador;
import com.evo.citicargasmobile.Helper.Formats;
import com.evo.citicargasmobile.Helper.Validations;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler  {

    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        try
        {
            String qrCode = Formats.unmask(rawResult.getText());
            if(qrCode != null && !qrCode.trim().isEmpty() && soContemNumeros(qrCode) && Validations.ValidarCpfCnpj(qrCode))
            {
                TransportadorRepository repository = new TransportadorRepository(getActivity());
                Transportador transportadorQR = repository.detalharTransportador(qrCode);

                if(transportadorQR.id <=0)
                {
                    Toast.makeText(getActivity(), " O QRCode apresentado não pertence um CPF/CNPJ cadastrado", Toast.LENGTH_SHORT).show();
                    mScannerView.startCamera();
                }else {
                    DadosTransportadorFragment result = new DadosTransportadorFragment();
                    result.transportador = transportadorQR;

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment f = fragmentManager.findFragmentByTag("tag");
                    fragmentTransaction.replace(R.id.container, result, "tag");
                    if (f != null) {
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    }
                    fragmentTransaction.commit();
                }
            }else{
                Toast.makeText(getActivity(), " O QRCode apresentado não pertence um CPF/CNPJ válido", Toast.LENGTH_SHORT).show();
                mScannerView.startCamera();
            }
        }
        catch (Exception ex)
        {
                Toast.makeText(getActivity(), " O QRCode apresentado não pertence um CPF/CNPJ válido", Toast.LENGTH_SHORT).show();
                mScannerView.startCamera();
        }

    }


    private boolean soContemNumeros(String texto) {
        if(texto == null)
            return false;
        for (char letra : texto.toCharArray()) {
            if (Character.isLetter(letra))
                return false;
        }
        return true;

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
