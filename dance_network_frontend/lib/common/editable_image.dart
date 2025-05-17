import 'package:dance_network_frontend/common/image_loading.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class EditableImage extends StatefulWidget {
  final String initialImageUrl;
  final Future<String> Function(UploadableFile) onImageChanged;
  final bool active;

  const EditableImage({
    super.key,
    required this.initialImageUrl,
    required this.onImageChanged,
    required this.active,
  });

  @override
  State<EditableImage> createState() => _EditableImageState();
}

class _EditableImageState extends State<EditableImage> {
  late String imageUrl;
  bool _isHovering = false;

  @override
  void initState() {
    super.initState();
    imageUrl = widget.initialImageUrl;
  }

  Future<void> _changeBackgroundImage() async {

    final UploadableFile? uploadableFile = await UniversalFilePicker.pickImage();

    if (uploadableFile != null) {
      final newImageUrl = await widget.onImageChanged(uploadableFile);
      setState(() {
        imageUrl = newImageUrl;
      });
    }
  }

  @override
  Widget build(BuildContext context) {

    Widget imageField = Image.network(
      imageUrl,
      width: double.infinity,
      fit: BoxFit.cover,
      loadingBuilder: (context, child, loadingProgress) => CustomLoadingBuilder(
        loadingProgress: loadingProgress,
        child: child,
      ),
      errorBuilder: (context, error, stackTrace) => const CustomErrorBuilder(),
    );

    if(!widget.active){
      return imageField;
    }

    return MouseRegion(
      onEnter: (_) => setState(() => _isHovering = true),
      onExit: (_) => setState(() => _isHovering = false),
      child: GestureDetector(
        onTap: _changeBackgroundImage,
        child: Stack(
          children: [
            imageField,
            if (_isHovering)
              Container(
              width: double.infinity,
              height: double.infinity,
              color: AppThemes.generateGradient(AppThemes.white)[7].withOpacity(0.12),
            ),
          ]
        )
      )
    );
  }
}

class UniversalFilePicker {
  static final ImagePicker _picker = ImagePicker();

  static Future<UploadableFile?> pickImage() async {
    final XFile? pickedFile = await _picker.pickImage(source: ImageSource.gallery);

    if (pickedFile == null) {
      return null;
    }

    final bytes = await pickedFile.readAsBytes();
    return UploadableFile(
      bytes: bytes,
      filename: pickedFile.name,
      contentType: _getMimeType(pickedFile.name),
    );
  }

  static String? _getMimeType(String filename) {
    if (filename.endsWith('.png')) return 'image/png';
    if (filename.endsWith('.jpg') || filename.endsWith('.jpeg')) return 'image/jpeg';
    return null;
  }
}

//TODO on andorid:
// <manifest xmlns:android="http://schemas.android.com/apk/res/android"
// package="com.example.yourapp">
//
// <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
// <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
// <uses-permission android:name="android.permission.CAMERA"/>
//
// <application
// ...
// >
// </application>
// </manifest>

// android {
// compileSdkVersion 34  // aktuell
//
// defaultConfig {
// minSdkVersion 21
// targetSdkVersion 34
// }
// }

//TODO macos
// <key>NSPhotoLibraryUsageDescription</key>
// <string>We need access to your photo library to upload images.</string>
// <key>NSCameraUsageDescription</key>
// <string>We need access to your camera to take pictures.</string>
// <key>NSMicrophoneUsageDescription</key>
// <string>We need access to your microphone for videos.</string>
// <key>NSDocumentsFolderUsageDescription</key>
// <string>We need access to your files to upload documents.</string>
