//
//  VoiceRecorderButton.swift
//  iosApp
//
//  Created by Javier Gutiérrez Bravo on 22/3/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct VoiceRecorderButton: View {
    var displayState: DisplayState
    var onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .foregroundColor(.primaryColor)
                    .padding()
                icon
                    .foregroundColor(.onPrimary)
            }
        }
        .frame(maxWidth: 100.0, maxHeight: 100.0)
        .accessibilityIdentifier("Voice recorder button")
    }
    
    var icon: some View {
        switch displayState {
            case .speaking:
                return Image(systemName: "stop.fill")
            case .displayingResult:
                return Image(systemName: "checkmark")
            default:
                return Image(uiImage: UIImage(named: "mic")!)
        }
    }
}

struct VoiceRecorderButton_Previews: PreviewProvider {
    static var previews: some View {
        VoiceRecorderButton(
            displayState: .waitingToTalk,
            onClick: {}
        )
    }
}
